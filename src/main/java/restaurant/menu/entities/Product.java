package restaurant.menu.entities;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;

    @Column(name = "number_item")
    private int numberItem;


    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "name_product")
    private String nameProduct;

    private double price;

    @Column(name = "image_product")
    private String imageProduct;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
