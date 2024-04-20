package restaurant.menu.entities.dto;

import lombok.Data;

@Data
public class PasswordDTo {
    private String oldPassword;
    private String newPassword;
}
