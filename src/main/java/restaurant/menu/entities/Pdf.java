package restaurant.menu.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Schema(description = "Entity PDF to summary the purchase")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pdf")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "PDF Id", example = "1")
    private int idPdf;

    @Lob
    @Column(name = "pdf_data")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "PDF name", example = "purchase_n_1")
    private byte[] pdfData;

    @Column(name = "number_order")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "number Order", example = "1231253")
    private String numberOrder;

}
