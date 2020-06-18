package nl.teama.server.repository;

import nl.teama.server.entity.Quiz;
import nl.teama.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findAllByUser(User user);
}
