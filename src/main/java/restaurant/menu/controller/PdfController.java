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
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.Role;
import restaurant.menu.exception.CustomEntityNotFoundException;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.PdfOperation;

import java.io.IOException;

/**
 * This class has the role to contain the endpoint {@link Customer}
 */

@RestController
@RequestMapping("/pdf")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "PdfController controller",description = "The role of this controller is keep all endpoints about pdf, every endpoint require a specific authentication")
public class PdfController {

    @Autowired
    private final PdfOperation pdfOperation;

    @PostMapping("/addPdf")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    @Operation(
            summary = "this endpoint works to createPdf",
            tags = {"PdfController", "post/addPdf"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Customer.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> createPdf(@RequestParam String number) {
        log.info("Starting method createPdf in class: " + getClass());
        try {
            pdfOperation.createPdf(number);
            return ResponseEntity.ok().body("Pdf created correctly");
        } catch (CustomEntityNotFoundException e) {
            log.error("Can't createPdf : " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ e.getMessage());  //TODO: provare nel caso a restituire un DTO come response formattato diversamente da questa response

        } catch (IOException e){
            return  ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ "Pdf already exist");
        }
    }




    @PostMapping("/retrievePdf")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    @Operation(
            summary = "this endpoint works to retrievePdf from the database based of the order",
            tags = {"PdfController", "post/retrievePdf"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Customer.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> retrievePdf(@RequestParam String OrderNumber) {
        log.info("Starting method retrievePdf in class: " + getClass());
        try {
            pdfOperation.processPdfFromDB(OrderNumber);
            return ResponseEntity.ok().body("Pdf retrieved correctly");
        } catch (CustomEntityNotFoundException e) {
            log.error("Can't retrieve the pdf : " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ e.getMessage());

        } catch (IOException e){
            return  ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ "Pdf already retrieved");
        }
    }


    @GetMapping("/getUnprocessedOrders")
    @Authorized(roles = {Role.ADMINISTRATOR,Role.CUSTOMER})
    @Operation(
            summary = "this endpoint works to retrieve unprocessed orders from the database",
            tags = {"PdfController", "get/getUnprocessedOrders"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Customer.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Params not valid", content = {@Content(schema = @Schema())})

    })
    public ResponseEntity<?> getUnprocessedOrders() {
        log.info("Starting method getUnprocessedOrders in class: " + getClass());
        try {
            return ResponseEntity.ok().body(pdfOperation.retrieveUnprocessedOrder());
        } catch (CustomEntityNotFoundException e) {
            log.error("Can't retrieve the orders : " + e.getMessage());
            return ResponseEntity.internalServerError().body(HttpStatus.BAD_REQUEST +" "+ e.getMessage());
        }
    }



}
