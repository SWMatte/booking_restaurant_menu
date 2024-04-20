package restaurant.menu.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entity Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Customer Id", example = "1")
    private int idCustomer;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Customer name", example = "Mirko")
    private String name;

    @Column(name = "last_name")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Customer lastName", example = "Pluti")
    private String lastName;

    @Column(name = "phone_number")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Customer phoneNumber", example = "384-342-34-12")
    private String phoneNumber;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Customer address", example = "Via fragolini 12")
    private String address;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
    //TODO: gestire la cancellazione Customer se lo cancelli-> cancelli  l'user, inserendo una deleteFlag true su user
    // -> quando deleteFlag è true l'user non puo autenticarsi -> sui prodotti non intacca

    //TODO: gestisci le query dove serve quando deleteFlag è falsa ad esempio modifica dati user o token

    @Column(name = "delete_flag")
    private boolean deleteFlag;
}
