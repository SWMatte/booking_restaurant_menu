package restaurant.menu.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import restaurant.menu.aspect.Authorized;
import restaurant.menu.entities.*;
import restaurant.menu.entities.dto.AuthenticationRequest;
import restaurant.menu.entities.dto.AuthenticationResponse;
import restaurant.menu.entities.dto.CustomerRequestDTO;
import restaurant.menu.exception.CustomEntityNotFoundException;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.UserSecurity;
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
    private final UserSecurity crudOperation;

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final CrudOperation<Customer> customerCrudOperation;


    @PostMapping("/register")
    public RegisterResponse register(@RequestBody CustomerRequestDTO request) throws Exception {
        log.info("Starting method register in class: " + getClass());

        try {
            User user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .deleteFlag(false)
                    .build();


            Customer customer = Customer.builder()
                    .address(request.getAddress())
                    .name(request.getName())
                    .lastName(request.getLastName())
                    .phoneNumber(request.getPhoneNumber())
                    .user(user)
                    .build();
            log.info("Starting save the user...");
            crudOperation.addElement(user);
            customerCrudOperation.addElement(customer);
            log.info("User saved successfully!");
            return new RegisterResponse(true);
        } catch (Exception e) {
            log.error("Can't save the User: " + e.getMessage());
            return new RegisterResponse(false);
        }
    }


    @PostMapping("/login")
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


    @PatchMapping("/updateEmail")
    @Authorized(roles = {Role.ADMINISTRATOR})
    public ResponseEntity<?> updateEmail(@RequestParam String email) {
        log.info("Starting method updateProduct in class: " + getClass());
        try {
            crudOperation.updateElement(email);
            log.info("updateEmail saved with success!");
            return ResponseEntity.ok().body("Product updated correctly");
        } catch (CustomEntityNotFoundException e) {
            log.error("Can't update the email: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST);

        }
    }

}
