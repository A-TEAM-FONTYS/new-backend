package nl.teama.server.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginDTO {
    @NotEmpty(message = "Please provide: email")
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "Please provide: password")
    private String password;
}
