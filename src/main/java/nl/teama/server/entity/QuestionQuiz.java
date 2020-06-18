package nl.teama.server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "question_quiz")
@Getter @Setter
public class QuestionQuiz extends BaseEntity implements Serializable {

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonBackReference
    private Quiz quiz;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @JsonUnwrapped
    private Question question;

    private String answer;

    public QuestionQuiz() {}

    public QuestionQuiz(Quiz quiz, Question question, String answer) {
        this.question = question;
        this.quiz = quiz;
        this.answer = answer;
    }
}
