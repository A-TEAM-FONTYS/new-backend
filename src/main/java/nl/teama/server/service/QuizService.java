package nl.teama.server.service;

import nl.teama.server.entity.Quiz;
import nl.teama.server.entity.User;
import nl.teama.server.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createOrUpdate(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    public Optional<Quiz> findById(UUID id) {
        return this.quizRepository.findById(id);
    }

    public List<Quiz> findAllById(User user) {
        return this.quizRepository.findAllByUser(user);
    }

    public void delete(Quiz quiz) {
        this.quizRepository.delete(quiz);
    }
}
