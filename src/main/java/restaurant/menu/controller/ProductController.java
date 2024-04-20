package restaurant.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import restaurant.menu.entities.Product;
import restaurant.menu.entities.RegisterResponse;
import restaurant.menu.entities.Role;
import restaurant.menu.entities.User;
import restaurant.menu.entities.dto.AuthenticationRequest;
import restaurant.menu.entities.dto.AuthenticationResponse;
import restaurant.menu.exception.EntityDeletedException;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.token.AuthenticationService;

/**
 * This class has the role to contain the endpoint {@link Product}
 */

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Product controller", description = "The role of this controller is keep all endpoints about customer, every endpoint require a specific authentication")
public class ProductController {

    @Autowired
    private final CrudOperation<Product> productCrudOperation;

    @PostMapping("/addProduct")
    @Authorized(roles = {Role.ADMINISTRATOR})
    @Operation(
            summary = "this endpoint works to register the products",
            tags = {"ProductController", "post/addProduct"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> registerProduct(
            @Parameter(description = "", required = true) @RequestBody Product element) {
        log.info("Starting method registerProduct in class: " + getClass());
        try {
            productCrudOperation.addElement(element);
            log.info("RegisterProduct saved with success!");
            return ResponseEntity.ok().body("Product added correctly");
        } catch (EntityNotFoundException e) {
            log.error("Can't find the entity: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e);
        } catch (EntityDeletedException e) {
            log.error("Can't find the entity because is deleted: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e);
        } catch (Exception e) {
            log.error("Can't save the Product: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST);

        }

    }

    @PatchMapping("/updateProduct")
    @Authorized(roles = {Role.ADMINISTRATOR})
    @Operation(
            summary = "this endpoint works to update the products",
            tags = {"ProductController", "post/updateProduct"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> updateProduct(
            @Parameter(description = "", required = true) @RequestBody Product element) {
        log.info("Starting method updateProduct in class: " + getClass());
        try {
            productCrudOperation.updateElement(element);
            log.info("UpdateProduct saved with success!");
            return ResponseEntity.ok().body("Product updated correctly");
        } catch (Exception e) {
            log.error("Can't update the Product: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/deleteProduct")
    @Authorized(roles = {Role.ADMINISTRATOR})
    @Operation(
            summary = "this endpoint works to delete the products",
            tags = {"ProductController", "post/deleteProduct"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Product.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> deleteProduct(
            @Parameter(description = "id to find and delete", required = true) @RequestParam int id) {
        log.info("Starting method deleteProduct in class: " + getClass());
        try {
            productCrudOperation.deleteElement(id);
            log.info("DeleteProduct finished correctly");
            return ResponseEntity.ok().body("Product deleted correctly");
        } catch (Exception e) {
            log.error("Can't delete the Product: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST);

        }
    }


}
