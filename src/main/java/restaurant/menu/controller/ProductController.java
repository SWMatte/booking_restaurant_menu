package restaurant.menu.controller;

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
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.token.AuthenticationService;

/**
 * This class has the role to contain the endpoint {@link Product}
 */

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final CrudOperation<Product> productCrudOperation;

    @PostMapping("/addProduct")
    @Authorized(roles = {Role.ADMINISTRATOR})
    public ResponseEntity<?> registerProduct(@RequestBody Product element) {
        log.info("Starting method registerProduct in class: " + getClass());
        try {
            productCrudOperation.addElement(element);
            log.info("RegisterProduct saved with success!");
            return ResponseEntity.ok().body("Product added correctly");
        } catch (Exception e) {
            log.error("Can't save the Product: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST);

        }
    }

    @PatchMapping("/updateProduct")
    @Authorized(roles = {Role.ADMINISTRATOR})
    public ResponseEntity<?> updateProduct(@RequestBody Product element) {
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
    public ResponseEntity<?> deleteProduct(@RequestParam int id) {
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
