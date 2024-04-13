package restaurant.menu.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
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
    private String numberOrder;

    @Column(name = "email_customer")
    private String emailCustomer;

    @Column(name = "address_customer")
    private String addressCustomer;

    @Column(name = "date_order")
    private LocalDate dateOrder;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;


}