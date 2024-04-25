package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.Product;
import restaurant.menu.entities.User;
import restaurant.menu.entities.dto.PdfFieldDTO;
import restaurant.menu.entities.dto.ProductPdfResponseDTO;
import restaurant.menu.repository.CustomerRepository;
import restaurant.menu.repository.OrderRepository;
import restaurant.menu.repository.PdfRepository;
import restaurant.menu.repository.ProductRepository;
import restaurant.menu.service.PdfOperation;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class PdfService implements PdfOperation {

    @Autowired
    private final PdfRepository pdfRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final ProductRepository productRepository;


    @Override
    public void createPdf(String numberOrder) {
        List<Order> order = orderRepository.findByNumberOrder(numberOrder);
        User user = orderRepository.getUserByEmailCustomerFromOrder(order.get(0).getEmailCustomer());
        Customer customer = customerRepository.findCustomerByUser(user);
        List<PdfFieldDTO> getPdfFieldDto = getPdfFieldDto(customer, order);
        try {

            File pdfFile = new File("restaurant_order.pdf");  // file che legge dalla root
            PDDocument pDDocument = PDDocument.load(pdfFile); // carica il file
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm(); // prende gli acroform segnati
            PDField field = pDAcroForm.getField("name_lastName"); // recupera ogni singolo acrofrom
            field.setValue(getPdfFieldDto.get(0).getName() + " " + getPdfFieldDto.get(0).getLastname());
            field = pDAcroForm.getField("email");
            field.setValue(getPdfFieldDto.get(0).getEmail());
            field = pDAcroForm.getField("indirizzo");
            field.setValue(getPdfFieldDto.get(0).getAddress());
            field = pDAcroForm.getField("data");
            field.setValue(getPdfFieldDto.get(0).getDateOrder());

            for (PdfFieldDTO value : getPdfFieldDto) {
                field = pDAcroForm.getField("nomeProdotto");
                field.setValue(value.getNameProduct());
                field = pDAcroForm.getField("prezzo");
                field.setValue(value.getPriceProduct());
                field = pDAcroForm.getField("tipo");
                field.setValue(value.getTypeProduct());

            }

            pDDocument.save("C:\\Users\\Administrator\\Desktop\\MATTEO\\output.pdf");
            pDDocument.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: RICREARE SU LIBRE OFFICE LA TABELLA CON GLI ACROFORM
        // TODO: 2) SETTARE METODO UNA VOLTA CREATO L'ORDINE CHE ALL'UTENTE RIMANGA L'ACQUISTO FATTO
        // TODO: 3) CONVERTIRE LO STESSO IN UN ARRAY DI BYTE E SALVARLO SUL DB INSIEME ALL'UTENTE CHE FA L'ACQUISTO
        // TODO: 4) RICONVERTIRLO IN PDF DA UN ALTRA PARTE ( COME SE FOSSE RICEVUTO DALL'AZIENDA A SEGUITO DELL'ORDINE EFFETTUATO)
        // TODO: 5) RENDERE DINAMICA LA CARTELLA IN CUI VIENE SALVATO A PRESCINDERE DAL S.O. DELL'UTENTE
        // TODO: 6) PRENDERE L'ARRAY DI BYTE QUINDI LO STESO PDF E INVIARLO ALL'UTENTE TRAMTIE EMAIL A ORDINE PROCESSATO

    }


    /**
     * Retrieve a list of Product based of amount of orders into Database, used in the method : getPdfFieldDto
     * <p>
     * The method iterate on orders list and get all products in that order then extract from them the name/price/type
     *
     * @param orders List of orders.
     * @return list of products {@link ProductPdfResponseDTO}.
     */
    private List<ProductPdfResponseDTO> getProducts(List<Order> orders) {
        List<Product> products = new ArrayList<>();
        List<ProductPdfResponseDTO> productsResponseDTO = new ArrayList<>();
        for (Order o : orders) {
            products.add(o.getProduct());
        }
        for (Product p : products) {
            productsResponseDTO.add(ProductPdfResponseDTO.builder()
                    .priceProduct(String.valueOf(p.getPrice()))
                    .nameProduct(p.getNameProduct())
                    .typeProduct(p.getType().name())
                    .build());
        }
        return productsResponseDTO;
    }

    /**
     * @param customer
     * @param order
     * @return {@link PdfFieldDTO } Retrieve a list used into : createPdf
     */
    private List<PdfFieldDTO> getPdfFieldDto(Customer customer, List<Order> order) {
        List<PdfFieldDTO> getPdfFieldDto = new ArrayList<>();

        for (ProductPdfResponseDTO p : getProducts(order)) {
            getPdfFieldDto.add(
                    PdfFieldDTO.builder()
                            .email(order.get(0).getEmailCustomer())
                            .address(order.get(0).getAddressCustomer())
                            .dateOrder(order.get(0).getDateOrder().toString())
                            .name(customer.getName())
                            .lastname(customer.getLastName())
                            .typeProduct(p.getTypeProduct())
                            .priceProduct(p.getPriceProduct())
                            .nameProduct(p.getNameProduct())
                            .build());
        }

        return getPdfFieldDto;


    }

}
