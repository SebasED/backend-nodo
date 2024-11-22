package estramipyme.service;

import estramipyme.dto.AnswerDto;
import estramipyme.model.Answer;
import estramipyme.model.Users;
import estramipyme.repository.AnswerRepository;
import estramipyme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    HashMap<String, Object> response_data;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAnswers(Long userId){
        response_data = new HashMap<>();

        Optional<Answer> optAnswer = this.answerRepository.findByUserId(userId);

        if (!optAnswer.isPresent()) {
            response_data.put("error", true);
            response_data.put("message", String.format("Answers by the user with id %d does not exists", userId));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        AnswerDto responseAnswer = new AnswerDto(optAnswer.get().getId(),
                optAnswer.get().getUser().getId(),
                optAnswer.get().getAnswers());

        return ResponseEntity.ok(responseAnswer);
    }

    public ResponseEntity<?> registerAnswers(AnswerDto answerDto){
        response_data = new HashMap<>();

        Optional<Users> optUser = userRepository.findById(answerDto.getUserId());
        // Verify if user exist
        if (!optUser.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("A user with id %d does not exists", answerDto.getUserId()));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        // Verify if answers by the user already exists

        boolean answerExists = answerRepository.existsByUserId(answerDto.getUserId());
        if (answerExists){
            response_data.put("error", true);
            response_data.put("message", String.format("A user with id %d already has answers", answerDto.getUserId()));
            response_data.put("status", HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response_data);
        }

        Answer answer = new Answer();
        answer.setAnswers(answerDto.getAnswers());
        answer.setUser(optUser.get());

        try {
            Answer answersSaved = this.answerRepository.save(answer);
            AnswerDto responseAnswer = new AnswerDto(answersSaved.getId(),
                    answersSaved.getUser().getId(),
                    answersSaved.getAnswers());
            /**URI questionLocation = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(answersSaved.getId())
                    .toUri();**/
            return ResponseEntity.status(HttpStatus.CREATED).body(responseAnswer);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }

    public ResponseEntity<?> updateAnswers(AnswerDto answerDto){
        response_data = new HashMap<>();

        Long userId = answerDto.getUserId();
        if (userId == null){
            response_data.put("error", true);
            response_data.put("message", "The user id is mandatory");
            response_data.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response_data);
        }

        Optional<Users> optUser = userRepository.findById(answerDto.getUserId());
        // Verify if user exist
        if (!optUser.isPresent()){
            response_data.put("error", true);
            response_data.put("message", String.format("A user with id %d does not exists", userId));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        Optional<Answer> optAnswer = this.answerRepository.findByUserId(answerDto.getUserId());

        // Verify if the answers exists
        if (!optAnswer.isPresent()) {
            response_data.put("error", true);
            response_data.put("message", String.format("The user with id %d does not have answers yet", userId));
            response_data.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response_data);
        }

        Answer answers = optAnswer.get();

        answers.setUser(optUser.get());
        answers.setAnswers(answerDto.getAnswers());

        try {
            Answer answersSaved = this.answerRepository.save(answers);
            AnswerDto responseAnswer = new AnswerDto(answersSaved.getId(),
                    answersSaved.getUser().getId(),
                    answersSaved.getAnswers());
            return ResponseEntity.status(HttpStatus.OK).body(responseAnswer);
        } catch (Exception e) {
            response_data.put("error", true);
            response_data.put("message", "Error del servidor: " + e.getMessage());
            response_data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response_data);
        }
    }
}
