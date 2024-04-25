package restaurant.menu.entities.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductPdfResponseDTO {

    private String nameProduct;
    private String priceProduct;
     private String typeProduct;

}
