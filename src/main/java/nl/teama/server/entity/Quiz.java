package nl.teama.server.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Quiz extends BaseEntity implements Serializable {

    @OneToOne
    private User user;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<QuestionQuiz> questions = new HashSet<>();

    public Quiz() {}

    public Quiz(User user) {
        this.user = user;
    }

    public void addAnswer(Question question, String answer) {
        this.questions.add(new QuestionQuiz(this, question, answer));
    }
}