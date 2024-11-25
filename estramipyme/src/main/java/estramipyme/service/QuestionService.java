package estramipyme.service;

import estramipyme.dto.QuestionRequestDto;
import estramipyme.dto.QuestionRequestPutDto;
import estramipyme.dto.QuestionResponseDto;
import estramipyme.model.Option;
import estramipyme.model.OptionQuestion;
import estramipyme.model.Question;
import estramipyme.model.Section;
import estramipyme.repository.OptionQuestionRepository;
import estramipyme.repository.OptionRepository;
import estramipyme.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final OptionQuestionRepository optionQuestionRepository;
    private final SectionService sectionService;
    HashMap<String, Object> response_data;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, SectionService sectionService, OptionRepository optionRepository, OptionQuestionRepository optionQuestionRepository) {
        this.questionRepository = questionRepository;
        this.sectionService = sectionService;
        this.optionRepository = optionRepository;
        this.optionQuestionRepository = optionQuestionRepository;
    }

    public List<QuestionResponseDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream().map(question -> {
            List<String> optionsResponse = Arrays.asList(question.getOptions().split(","));

            return new QuestionResponseDto(question.getId(),
                    question.getSection().getDescription(),
                    question.getDescription(),
                    optionsResponse);
        }).toList();
    }

    public ResponseEntity<?> getQuestionsBySection(String section) {
        response_data = new HashMap<>();

        Optional<Section> optionalSection = this.sectionService.getSectionByDescription(section);
        if(!optionalSection.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("%s section does not exists", section));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        List<Question> questions = questionRepository.findBySection_Id(optionalSection.get().getId());

        List<QuestionResponseDto> questionResponseDtos = questions.stream().map(question -> {
            List<String> optionsResponse = Arrays.asList(question.getOptions().split(","));

            return new QuestionResponseDto(
                    question.getId(),
                    question.getSection().getDescription(),
                    question.getDescription(),
                    optionsResponse
            );
        }).toList();
        return ResponseEntity.ok(questionResponseDtos);

    }



    public ResponseEntity<?> getQuestion(Long id) {
        response_data = new HashMap<>();

        Optional<Question> optionalQuestion = this.questionRepository.findById(id);

        if (!optionalQuestion.isPresent()) {
            response_data.put("error", true);
            response_data.put("message", String.format("A question with id %d does not exists", id));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }
        Question question = optionalQuestion.get();
        List<String> optionsResponse = Arrays.asList(question.getOptions().split(","));
        QuestionResponseDto questionResponse = new QuestionResponseDto(question.getId(),
                question.getSection().getDescription(),
                question.getDescription(),
                optionsResponse);

        return ResponseEntity.ok(questionResponse);
    }

    @Transactional
    public ResponseEntity<?> registerQuestion(QuestionRequestDto question) {
        response_data = new HashMap<>();

        // Verify if question exists
        boolean questionExists = this.questionRepository.existsByDescription(question.getQuestion());
        if (questionExists) {
            response_data.put("error", true);
            response_data.put("message", String.format("The question %s alresdy exists", question.getQuestion()));
            response_data.put("status", HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
        }

        // Section validations
        String section = question.getSection();
        Optional<Section> optionalSection = this.sectionService.getSectionByDescription(section);
        if (!optionalSection.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("The section with %s does not exists", section));
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        }

        try {
            // Create question to save
            Question questionEntity = new Question();
            questionEntity.setSection(optionalSection.get());
            questionEntity.setDescription(question.getQuestion());
            String options = question.getOptions().stream().collect(Collectors.joining(","));
            questionEntity.setOptions(options);
            Question questionSaved = this.questionRepository.save(questionEntity);
            // Create a URI by the new question
            URI questionLocation = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(questionSaved.getId())
                    .toUri();
            // Create the question response
            List<String> optionsResponse = Arrays.asList(questionSaved.getOptions().split(","));
            QuestionResponseDto questionResponse = new QuestionResponseDto(questionSaved.getId(),
                    questionSaved.getSection().getDescription(),
                    questionSaved.getDescription(),
                    optionsResponse);
            return ResponseEntity.created(questionLocation).body(questionResponse);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }

    public ResponseEntity<?> updateQuestion(QuestionRequestPutDto question) {
        response_data = new HashMap<>();

        // Verify if id is in the request
        if (question.getId() == null) {
            response_data.put("error", true);
            response_data.put("message", "The id is mandatory for update a question");
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        }

        // verify if id exists in the database
        boolean idExists = this.questionRepository.existsById(question.getId());
        if (!idExists) {
            response_data.put("error", true);
            response_data.put("message", String.format("The id %d does not exists", question.getId()));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        // Verify if question exists
//        boolean questionExists = this.questionRepository.existsByDescription(question.getQuestion());
//        if (questionExists) {
//            response_data.put("error", true);
//            response_data.put("message", String.format("The question %s alresdy exists", question.getQuestion()));
//            response_data.put("status", HttpStatus.CONFLICT.value());
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
//        }
        // Section validations
        String section = question.getSection();
        Optional<Section> optionalSection = this.sectionService.getSectionByDescription(section);
        if (!optionalSection.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("The section with %s does not exists", section));
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        }
        // Create question to update
        Question questionEntity = new Question();
        questionEntity.setId(question.getId());
        questionEntity.setSection(optionalSection.get());
        questionEntity.setDescription(question.getQuestion());
        String options = question.getOptions().stream().collect(Collectors.joining(","));
        questionEntity.setOptions(options);


        try {
            // Create the question response
            Question questionUpdated = this.questionRepository.save(questionEntity);
            List<String> optionsResponse = Arrays.asList(questionUpdated.getOptions().split(","));
            QuestionResponseDto questionResponse = new QuestionResponseDto(questionUpdated.getId(),
                    questionUpdated.getSection().getDescription(),
                    questionUpdated.getDescription(),
                    optionsResponse);

            return ResponseEntity.ok().body(questionResponse);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteQuestion(Long id) {
        response_data = new HashMap<>();

        // Get question by id and verify if id exists in the database
        Optional<Question> optionalQuestion = this.questionRepository.findById(id);

        if (!optionalQuestion.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("The id %d does not exists",id));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        try {
            this.questionRepository.delete(optionalQuestion.get());
            List<String> optionsResponse = Arrays.asList(optionalQuestion.get().getOptions().split(","));
            QuestionResponseDto questionResponse = new QuestionResponseDto(optionalQuestion.get().getId(),
                    optionalQuestion.get().getSection().getDescription(),
                    optionalQuestion.get().getDescription(),
                    optionsResponse);
            return ResponseEntity.ok().body(questionResponse);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }
}
