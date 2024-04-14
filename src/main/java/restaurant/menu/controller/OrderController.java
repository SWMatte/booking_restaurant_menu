package restaurant.menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.menu.aspect.Authorized;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.Role;
import restaurant.menu.entities.User;
import restaurant.menu.entities.dto.OrderRequestDTO;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.OrderOperation;

/**
 * This class has the role to contain the endpoint {@link Order}
 */

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderOperation orderOperation;

    @PostMapping("/addOrder")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    public ResponseEntity<?> registerOrder(@RequestBody OrderRequestDTO element) {
        log.info("Starting method registerCustomer in class: " + getClass());
        try {

            orderOperation.addOrder(element);
             log.info("registerOrder saved with success!");
            return ResponseEntity.ok().body("Product added correctly");
        } catch (Exception e) {
            log.error("Can't save the order: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST);

        }
    }




}
