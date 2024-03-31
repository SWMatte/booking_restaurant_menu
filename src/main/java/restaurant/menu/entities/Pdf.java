package restaurant.menu.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Pdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pdf")
    private int idPdf;

    @Column(name = "name_pdf")
    private String namePdf;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

}
