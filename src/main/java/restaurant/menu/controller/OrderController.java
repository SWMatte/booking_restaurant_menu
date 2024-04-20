package restaurant.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.menu.aspect.Authorized;
import restaurant.menu.entities.*;
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
@Tag(name = "Order controller",description = "The role of this controller is keep all endpoints about customer, every endpoint require a specific authentication")
public class OrderController {

    @Autowired
    private final OrderOperation orderOperation;

    @PostMapping("/addOrder")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    @Operation(
            summary = "this endpoint works to register the orders",
            tags = {"ProductController", "post/addOrder"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Order.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
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
