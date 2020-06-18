package nl.teama.server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Question extends BaseEntity implements Serializable {

    private int priority;

    private String name;

    private String icon;

    private String remark;

    private String type;

    private String category;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonBackReference
    @Getter
    private Set<QuestionQuiz> quizSet = new HashSet<>();

    public Question() {}
}
