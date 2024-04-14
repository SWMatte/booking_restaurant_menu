package restaurant.menu.entities.dto;

import lombok.Data;
import restaurant.menu.entities.User;

import java.util.List;

@Data
public class OrderRequestDTO {
    List<Integer> idProduct;
    String address;
    String  emailUser;
}
