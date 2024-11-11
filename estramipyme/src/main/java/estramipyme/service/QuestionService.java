package estramipyme.service;

import estramipyme.model.Question;
import estramipyme.model.Section;
import estramipyme.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SectionService sectionService;
    HashMap<String, Object> response_data;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, SectionService sectionService) {
        this.questionRepository = questionRepository;
        this.sectionService = sectionService;
    }

    public List<Question> getAllQuestions() {
        return this.questionRepository.findAll();
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

        return ResponseEntity.ok(optionalQuestion.get());
    }

    @Transactional
    public ResponseEntity<?> registerQuestion(Question question) {
        response_data = new HashMap<>();

        // Verify if question exists
        boolean questionExists = this.questionRepository.existsByDescription(question.getDescription());
        if (questionExists) {
            response_data.put("error", true);
            response_data.put("message", String.format("The question %s alresdy exists", question.getDescription()));
            response_data.put("status", HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
        }

        // Section validations
        Long sectionId = question.getSection().getId();
        Optional<Section> optionalSection = this.sectionService.getSection(sectionId);
        if (!optionalSection.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("The section with id %d does not exists", sectionId));
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        } else if (!optionalSection.get().getDescription().equals(question.getSection().getDescription())) {
            response_data.put("error", true);
            response_data.put("message", String
                    .format("The section with id %d is %s, but you sent the section %s",
                            sectionId,
                            optionalSection.get().getDescription(),
                            question.getSection().getDescription()));
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        }

        try {
            Question questionSaved = this.questionRepository.save(question);
            URI questionLocation = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(questionSaved.getId())
                    .toUri();
            return ResponseEntity.created(questionLocation).body(questionSaved);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }

    public ResponseEntity<?> updateQuestion(Question question) {
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
        boolean questionExists = this.questionRepository.existsByDescription(question.getDescription());
        if (questionExists) {
            response_data.put("error", true);
            response_data.put("message", String.format("The question %s alresdy exists", question.getDescription()));
            response_data.put("status", HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
        }

        try {
            Question questionUpdated = this.questionRepository.save(question);
            return ResponseEntity.ok().body(questionUpdated);
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
            return ResponseEntity.ok().body(optionalQuestion.get());
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }
}
