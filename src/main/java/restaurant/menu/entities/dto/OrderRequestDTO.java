package restaurant.menu.entities.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    List<Integer> idProduct;
    String address;
}
