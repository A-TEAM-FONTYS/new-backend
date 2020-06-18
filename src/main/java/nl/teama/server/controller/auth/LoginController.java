package nl.teama.server.controller.auth;

import nl.teama.server.config.jwt.TokenProvider;
import nl.teama.server.controller.enums.AuthResponse;
import nl.teama.server.entity.User;
import nl.teama.server.logic.PasswordHelper;
import nl.teama.server.models.LoginDTO;
import nl.teama.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordHelper passwordHelper;

    @Autowired
    public LoginController(UserService userService, TokenProvider tokenProvider, PasswordHelper passwordHelper) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordHelper = passwordHelper;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginModel) {
        Optional<User> user = userService.findUserByEmail(loginModel.getEmail());

        if(!user.isPresent()) {
            return new ResponseEntity<>(AuthResponse.WRONG_CREDENTIALS.toString(), HttpStatus.BAD_REQUEST);
        }

        String password = user.get().getPassword();

        if(!this.passwordHelper.isMatch(loginModel.getPassword(), password)) {
            return new ResponseEntity<>(AuthResponse.WRONG_CREDENTIALS.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            Map<Object, Object> model = new LinkedHashMap<>();
            model.put("token", tokenProvider.createToken(user.get().getId(), user.get().getFirstName(), user.get().getLastName(), user.get().getRole()));
            model.put("user", user.get());
            return ok(model);
        } catch(AuthenticationException ex) {
            logger.error("Error on login: %s", ex);
            return new ResponseEntity<>(AuthResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
