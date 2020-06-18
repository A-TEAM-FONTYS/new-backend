package nl.teama.server.controller;

import nl.teama.server.controller.enums.DataResponse;
import nl.teama.server.entity.Quiz;
import nl.teama.server.entity.User;
import nl.teama.server.logic.QuizLogic;
import nl.teama.server.models.QuizDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/quiz")
public class QuizController {
    private final QuizLogic quizLogic;

    @Autowired
    public QuizController(QuizLogic quizLogic) {
        this.quizLogic = quizLogic;
    }

    @PostMapping
    public ResponseEntity createQuiz(@AuthenticationPrincipal User user) {
        Quiz quiz = this.quizLogic.createQuiz(user);

        if(quiz != null) {
            return ResponseEntity.ok(quiz);
        }

        return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getQuiz(@PathVariable String id) {
        Optional<Quiz> quiz = this.quizLogic.findById(UUID.fromString(id));

        if(quiz.isPresent()) {
            return ResponseEntity.ok(quiz.get());
        }

        return new ResponseEntity<>("No quiz has been found.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity getAllQuiz(@AuthenticationPrincipal User user) {
        List<Quiz> quizes = this.quizLogic.findAllByUser(user);

        if(!quizes.isEmpty()) {
            return ResponseEntity.ok(quizes);
        }

        return new ResponseEntity<>("No quiz has been found.", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateQuiz(@RequestBody @Valid QuizDTO dto, @PathVariable String id) {
        Quiz quiz = this.quizLogic.updateQuiz(dto, UUID.fromString(id));

        if(quiz != null) {
            return ResponseEntity.ok(quiz);
        }

        return new ResponseEntity<>(DataResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteQuiz(@PathVariable String id) {
        this.quizLogic.delete(UUID.fromString(id));

        return new ResponseEntity<>("Quiz deleted", HttpStatus.OK);
    }
}
