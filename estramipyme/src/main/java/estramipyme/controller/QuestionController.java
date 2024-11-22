package estramipyme.controller;

import estramipyme.dto.QuestionRequestDto;
import estramipyme.dto.QuestionResponseDto;
import estramipyme.model.Question;
import estramipyme.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {

        return ResponseEntity.ok(this.questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestion(@PathVariable Long id) {
        return this.questionService.getQuestion(id);
    }

    @PostMapping
    public ResponseEntity<?> registerQuestion(@RequestBody QuestionRequestDto question) {
        return this.questionService.registerQuestion(question);
    }

    @PutMapping
    public ResponseEntity<?> updateQuestion(@RequestBody Question question){
        return this.questionService.updateQuestion(question);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id){
        return this.questionService.deleteQuestion(id);
    }
}
