package nl.teama.server.logic;

import nl.teama.server.entity.Question;
import nl.teama.server.entity.Quiz;
import nl.teama.server.entity.User;
import nl.teama.server.models.QuizDTO;
import nl.teama.server.service.QuestionService;
import nl.teama.server.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class QuizLogic {
    private final QuizService quizService;
    private final QuestionService questionService;

    @Autowired
    public QuizLogic(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    public Quiz createQuiz(User user) {
        Quiz quiz = new Quiz(user);
        return this.quizService.createOrUpdate(quiz);
    }

    public Optional<Quiz> findById(UUID uuid) {
        return this.quizService.findById(uuid);
    }

    public void delete(UUID uuid) {
        Optional<Quiz> quiz = this.quizService.findById(uuid);
        quiz.ifPresent(this.quizService::delete);
    }

    public List<Quiz> findAllByUser(User user) {
        return this.quizService.findAllById(user);
    }

    public Quiz updateQuiz(QuizDTO dto, UUID id) {
        Optional<Quiz> foundQuiz = this.quizService.findById(id);

        if(foundQuiz.isPresent()) {

            for(int i = 0; i < dto.getQuestions().size(); i++) {

                Optional<Question> question = this.questionService.findById(UUID.fromString(dto.getQuestions().get(i).getQuestionId()));

                if(!question.isPresent() || dto.getQuestions().get(i).getAnswer().isEmpty()) {
                    return null;
                }

                foundQuiz.get().addAnswer(question.get(), dto.getQuestions().get(i).getAnswer());
            }

            return this.quizService.createOrUpdate(foundQuiz.get());

        }

        return null;
    }
}
