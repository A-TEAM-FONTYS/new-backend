package nl.teama.server.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DataDTO {
    @NotEmpty(message = "Please provide: App Name")
    private String appName;

    @NotNull(message = "Please provide: Time Used")
    private Long timeUsed;
}
