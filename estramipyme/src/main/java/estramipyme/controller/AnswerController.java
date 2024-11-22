package estramipyme.controller;

import estramipyme.dto.AnswerDto;
import estramipyme.dto.UserResponseDto;
import estramipyme.model.Answer;
import estramipyme.repository.UserRepository;
import estramipyme.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@CrossOrigin(origins = "*")
public class AnswerController {

    final private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAnswers(@PathVariable Long userId){
        return this.answerService.getAnswers(userId);
    }

    @PostMapping
    public ResponseEntity<?> registerAnswers(@RequestBody AnswerDto answers){
        return this.answerService.registerAnswers(answers);
    }

    @PutMapping
    public ResponseEntity<?> updateAnswers(@RequestBody AnswerDto answers){
        return this.answerService.updateAnswers(answers);
    }
}
