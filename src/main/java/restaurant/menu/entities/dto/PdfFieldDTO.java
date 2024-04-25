package restaurant.menu.entities.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PdfFieldDTO {

    private String name;
    private String lastname;
    private String email;
    private String address;
    private String dateOrder;
    private String nameProduct;
    private String priceProduct;
    private String typeProduct;
}
