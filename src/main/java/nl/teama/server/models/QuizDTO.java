package nl.teama.server.models;

import lombok.Data;

import java.util.List;

@Data
public class QuizDTO {
    private List<QuestionDTO> questions;
}
