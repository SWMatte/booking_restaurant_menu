package restaurant.menu.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
@Schema(description = "Entity Order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Order Id", example = "1")
    private int idOrder;

    @Column(name = "number_order")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Order number", example = "9c0bd87c-f3fb-4d34-9754-3aa8d2b188c0")
    private String numberOrder;

    @Column(name = "email_customer")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Order email", example = "a@b.it")
    private String emailCustomer;

    @Column(name = "address_customer")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Order address", example = "Via castegneti 12")
    private String addressCustomer;

    @Column(name = "date_order")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Order date", example = "2024-01-23")
    private LocalDate dateOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "product Id", example = "1")
    private Product product;


    // TODO: per risalire al customer che ha effettuato l'ordine con nome ecc, puoi utilizzare con la clausola with in sql
    //       fai ritornare l'id user usando l'email che trovi nell'order quell'id lo usi nel customer per trovare nome congome di chi ha effettuato l'ordine
    //      quindi puoi poi utilizzare quel dato per magari fare il cart o un riepilogo ( magari anche pdf)


}