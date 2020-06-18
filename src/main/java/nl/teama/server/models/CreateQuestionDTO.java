package nl.teama.server.models;

import lombok.Data;

@Data
public class CreateQuestionDTO {
    private int priority;

    private String name;

    private String icon;

    private String remark;

    private String type;

    private String category;
}
