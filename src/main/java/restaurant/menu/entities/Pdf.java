package restaurant.menu.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(name = "document_processed")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "document processed", example = "false")
    private boolean documentProcessed;

    @Column(name = "date_saved")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "date saved", example = "2024-03-12")
    private LocalDate dateSaved;


}
