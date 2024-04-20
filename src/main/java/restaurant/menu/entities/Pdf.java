package restaurant.menu.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Schema(description = "Entity PDF to summary the purchase")
public class Pdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pdf")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "PDF Id", example = "1")
    private int idPdf;

     @Column(name = "name_pdf")
     @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "PDF name", example = "purchase_n_1")
     private String namePdf;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

}
