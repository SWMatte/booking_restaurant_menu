package restaurant.menu.entities;

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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private int idCustomer;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
    //TODO: gestire la cancellazione Customer se lo cancelli-> cancelli  l'user, inserendo una deleteFlag true su user
    // -> quando deleteFlag è true l'user non puo autenticarsi -> sui prodotti non intacca

    //TODO: gestisci le query dove serve quando deleteFlag è falsa ad esempio modifica dati user o token

}
