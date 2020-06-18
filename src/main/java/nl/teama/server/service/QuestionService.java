package nl.teama.server.service;

import nl.teama.server.entity.Question;
import nl.teama.server.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createOrUpdate(Question question) {
        return this.questionRepository.save(question);
    }

    public Optional<Question> findById(UUID id) {
        return this.questionRepository.findById(id);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public List<Question> findByCategory(String category) {
        return this.questionRepository.findAllByCategory(category);
    }
}
