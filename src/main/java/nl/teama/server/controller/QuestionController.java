package nl.teama.server.controller;

import nl.teama.server.controller.enums.DataResponse;
import nl.teama.server.entity.Question;
import nl.teama.server.logic.QuestionLogic;
import nl.teama.server.models.CreateQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    private final QuestionLogic questionLogic;

    @Autowired
    public QuestionController(QuestionLogic questionLogic) {
        this.questionLogic = questionLogic;
    }

    @PostMapping
    public ResponseEntity createQuestion(@RequestBody @Valid CreateQuestionDTO dto) {
        Question question = this.questionLogic.createOrUpdate(dto);

        if(question != null) {
            return ResponseEntity.ok(question);
        }

        return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{category}/category")
    public ResponseEntity getAllByCategory(@PathVariable String category) {
        List<Question> questions = this.questionLogic.findByCategory(category);

        if(!questions.isEmpty()) {
            return ResponseEntity.ok(questions);
        }

        return new ResponseEntity<>("No Questions are found.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<Question> question = this.questionLogic.findById(UUID.fromString(id));

        if(question.isPresent()) {
            return ResponseEntity.ok(question.get());
        }

        return new ResponseEntity<>("No question found.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateQuestion(@PathVariable String id, @Valid @RequestBody CreateQuestionDTO dto) {
        Question question = this.questionLogic.updateQuestion(UUID.fromString(id), dto);

        if(question != null) {
            return ResponseEntity.ok(question);
        }

        return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteQuestion(@PathVariable String id) {
        this.questionLogic.delete(UUID.fromString(id));

        return new ResponseEntity<>("Question deleted", HttpStatus.OK);
    }
}
