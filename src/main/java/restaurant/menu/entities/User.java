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
@Schema(description = "Entity User for authentication")
public class User {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User Id", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User email", example = "a@b.it")
    private String email;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User password", example = "1234")
    private String password;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "deleted", example = "true")
    @Column(name = "delete_flag")
    private boolean deleteFlag;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Role entity Id", example = "1")
    @Enumerated(EnumType.STRING)
    private Role role;

}

