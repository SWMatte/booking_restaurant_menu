package restaurant.menu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.menu.aspect.Authorized;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Product;
import restaurant.menu.entities.Role;
import restaurant.menu.exception.CustomEntityNotFoundException;
import restaurant.menu.service.CrudOperation;

/**
 * This class has the role to contain the endpoint {@link Customer}
 */

@RestController
@RequestMapping("/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CrudOperation<Customer> customerCrudOperation;

    @PatchMapping("/updateCustomer")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    public ResponseEntity<?> updateCustomer(@RequestBody Customer element) {
        log.info("Starting method updateCustomer in class: " + getClass());
        try {
            customerCrudOperation.updateElement(element);
            log.info("UpdateCustomer saved with success!");
            return ResponseEntity.ok().body("Customer updated correctly");
        } catch (CustomEntityNotFoundException e) {
            log.error("Can't update the Customer: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ e.getMessage());

        }
    }

    @DeleteMapping("/deleteCustomer")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    public ResponseEntity<?> deleteCustomer(@RequestParam int id) {
        log.info("Starting method deleteCustomer in class: " + getClass());
        try {
            customerCrudOperation.deleteElement(id);
            log.info("DeleteCustomer finished correctly");
            return ResponseEntity.ok().body("Customer deleted correctly");
        }catch (CustomEntityNotFoundException e) {
            log.error("Can't delete the Customer: " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ e.getMessage());

        }
    }



}
