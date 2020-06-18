package nl.teama.server.controller.auth;

import nl.teama.server.config.jwt.TokenProvider;
import nl.teama.server.controller.enums.AuthResponse;
import nl.teama.server.entity.User;
import nl.teama.server.logic.PasswordHelper;
import nl.teama.server.models.RegisterDTO;
import nl.teama.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class RegisterController {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordHelper passwordHelper;

    @Autowired
    public RegisterController(UserService userService, TokenProvider tokenProvider, PasswordHelper passwordHelper) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordHelper = passwordHelper;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO registerModel) {
        if(userService.findUserByEmail(registerModel.getEmail()).isPresent()) {
            return new ResponseEntity<>(AuthResponse.USER_ALREADY_EXISTS.toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            User user = new User();
            user.setEmail(registerModel.getEmail());
            user.setFirstName(registerModel.getFirstName());
            user.setLastName(registerModel.getLastName());
            user.setPassword(passwordHelper.hash(registerModel.getPassword()));

            User createdUser = userService.createOrUpdate(user);

            Map<Object, Object> model = new LinkedHashMap<>();
            model.put("token", tokenProvider.createToken(createdUser.getId(), createdUser.getFirstName(), createdUser.getLastName(), createdUser.getRole()));
            model.put("user", createdUser);
            return ok(model);
        } catch(Exception ex) {
            logger.error("Error on register: %s", ex);
            return new ResponseEntity<>(AuthResponse.UNEXPECTED_ERROR.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
