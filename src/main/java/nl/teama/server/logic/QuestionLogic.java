package nl.teama.server.logic;

import nl.teama.server.entity.Question;
import nl.teama.server.models.CreateQuestionDTO;
import nl.teama.server.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class QuestionLogic {
    private final QuestionService questionService;

    @Autowired
    public QuestionLogic(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Question createOrUpdate(CreateQuestionDTO dto) {
        Question question = new Question();
        question.setCategory(dto.getCategory());
        question.setIcon(dto.getIcon());
        question.setName(dto.getName());
        question.setPriority(dto.getPriority());
        question.setType(dto.getType());
        question.setRemark(dto.getRemark());

        return this.questionService.createOrUpdate(question);
    }

    public Optional<Question> findById(UUID id) {
        return this.questionService.findById(id);
    }

    public List<Question> findByCategory(String category) {
        return this.questionService.findByCategory(category);
    }

    public void delete(UUID id) {
        Optional<Question> quiz = this.questionService.findById(id);
        quiz.ifPresent(this.questionService::delete);
    }

    public Question updateQuestion(UUID id, CreateQuestionDTO dto) {
        Optional<Question> question = this.questionService.findById(id);

        if(question.isPresent()) {

            if(!dto.getCategory().isEmpty()) {
                question.get().setCategory(dto.getCategory());
            }

            if(!dto.getIcon().isEmpty()) {
                question.get().setIcon(dto.getIcon());
            }

            if(!dto.getName().isEmpty()) {
                question.get().setName(dto.getName());
            }

            if(dto.getPriority() != 0) {
                question.get().setPriority(dto.getPriority());
            }

            if(!dto.getRemark().isEmpty()) {
                question.get().setRemark(dto.getRemark());
            }

            if(!dto.getType().isEmpty()) {
                question.get().setType(dto.getType());
            }

            return this.questionService.createOrUpdate(question.get());
        }

        return null;
    }
}
