package restaurant.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
import restaurant.menu.entities.dto.PasswordDTo;
import restaurant.menu.exception.CustomEntityNotFoundException;
import restaurant.menu.exception.PasswordEncoderException;
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
@Tag(name = "Product controller", description = "The role of this controller is keep all endpoints about customer, every endpoint require a specific authentication")
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
    @Operation(
            summary = "this endpoint works to register customers and set them one authentication",
            tags = {"userController", "post/register"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public RegisterResponse register(
            @Parameter(description = "ClassDTO to register the customer", required = true) @RequestBody CustomerRequestDTO request) throws Exception {
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
    @Operation(
            summary = "this endpoint works to allow the login for customers and supply them the token to allow to call other endpoint",
            tags = {"userController", "post/login"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
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
    @Operation(
            summary = "this endpoint allow to update the email of customer",
            tags = {"userController", "patch/login"}
    )
    @Authorized(roles = {Role.ADMINISTRATOR})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
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


    @PostMapping("/changePassword")
    @Operation(
            summary = "this endpoint allow to change the password",
            tags = {"userController", "post/changePassword"}
    )
    @Authorized(roles = {Role.ADMINISTRATOR})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTo passwordDTo) {
        try {
            crudOperation.changePassword(passwordDTo);
            return ResponseEntity.ok("Password changed correctly");

        } catch (PasswordEncoderException e) {
            return ResponseEntity.badRequest().body(e);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
