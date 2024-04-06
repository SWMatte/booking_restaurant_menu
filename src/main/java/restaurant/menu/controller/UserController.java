package restaurant.menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.menu.aspect.Authorized;
import restaurant.menu.entities.RegisterResponse;
import restaurant.menu.entities.Role;
import restaurant.menu.entities.User;
import restaurant.menu.entities.dto.AuthenticationRequest;
import restaurant.menu.entities.dto.AuthenticationResponse;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.token.AuthenticationService;

/**
 * This class has the role to contain the endpoint {@link User}
 */

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final CrudOperation<User> crudOperation;

    @Autowired
    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody User request) throws Exception {
        log.info("Starting method register in class: " + getClass());
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .deleteFlag(false)
                .build();
        try {
            log.info("Starting save the user...");
            crudOperation.addElement(user);
            log.info("User saved successfully!");
            return new RegisterResponse(true);
        } catch (Exception e) {
            log.error("Can't save the User: " + e.getMessage());
            return new RegisterResponse(false);
        }
    }





    @PostMapping("/login")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Trying to authenticate the user!");
        try {
            AuthenticationResponse authorized = authenticationService.authenticate(request);
            return ResponseEntity.ok(authorized);
        } catch (Exception e) {
            log.error("User unauthorized! Message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
