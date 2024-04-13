package restaurant.menu.entities.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import restaurant.menu.entities.Role;

@Data
public class CustomerRequestDTO {
    // User Entity
    private String email;

    private String password;

    private boolean deleteFlag;

    private Role role;

    //Customer Entity
    private String name;

    private String lastName;

    private String phoneNumber;

    private String address;

}
