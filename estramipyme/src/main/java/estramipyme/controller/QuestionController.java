package estramipyme.controller;

import estramipyme.model.Question;
import estramipyme.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {

        return ResponseEntity.ok(questionService.getAllQuestions());
    }
}
