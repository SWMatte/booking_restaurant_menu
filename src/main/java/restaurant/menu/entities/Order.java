package restaurant.menu.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private int idOrder;

    @Column(name = "number_order")
    private int numberOrder;

    @Column(name = "email_customer")
    private String emailCustomer;

    @Column(name = "address_customer")
    private String addressCustomer;


    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;


}