package restaurant.menu.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private int idOrder;

    @Column(name = "number_order")
    private String numberOrder;

    @Column(name = "email_customer")
    private String emailCustomer;

    @Column(name = "address_customer")
    private String addressCustomer;

    @Column(name = "date_order")
    private LocalDate dateOrder;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;


    // TODO: per risalire al customer che ha effettuato l'ordine con nome ecc, puoi utilizzare con la clausola with in sql
    //       fai ritornare l'id user usando l'email che trovi nell'order quell'id lo usi nel customer per trovare nome congome di chi ha effettuato l'ordine
    //      quindi puoi poi utilizzare quel dato per magari fare il cart o un riepilogo ( magari anche pdf)


}