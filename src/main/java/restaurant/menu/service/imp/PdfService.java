package restaurant.menu.service.imp;

import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant.menu.entities.*;
import restaurant.menu.entities.dto.PdfFieldDTO;
import restaurant.menu.entities.dto.ProductPdfResponseDTO;
import restaurant.menu.entities.dto.UnprocessedOrderDTO;
import restaurant.menu.repository.*;
import restaurant.menu.service.PdfOperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    @Autowired
    private final UserRepository userRepository;


    @Override
    public PDDocument createPdf(String numberOrder) throws IOException {
        String operationSystem = getPathDesktop();

        List<Order> order = orderRepository.findByNumberOrder(numberOrder);
        User user = userRepository.findByEmail(order.get(0).getEmailCustomer()).get();
        Customer customer = customerRepository.findCustomerByUser(user);
        List<PdfFieldDTO> getPdfFieldDto = getPdfFieldDto(customer, order);
        try {

            File pdfFile = new File("restaurant_order.pdf");  // file che legge dalla root
            PDDocument pDDocument = PDDocument.load(pdfFile); // carica il file
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm(); // prende gli acroform segnati
            PDField field = pDAcroForm.getField("name_lastName"); // recupera ogni singolo acrofrom
            field.setValue(getPdfFieldDto.get(0).getName() + " " + getPdfFieldDto.get(0).getLastname());
            field.setReadOnly(true);
            field = pDAcroForm.getField("email");
            field.setReadOnly(true);
            field.setValue(getPdfFieldDto.get(0).getEmail());
            field = pDAcroForm.getField("indirizzo");
            field.setReadOnly(true);
            field.setValue(getPdfFieldDto.get(0).getAddress());
            field = pDAcroForm.getField("data");
            field.setReadOnly(true);
            field.setValue(getPdfFieldDto.get(0).getDateOrder());

            PDPage page = pDDocument.getPage(0); // prendi la pagina 0
            PDPageContentStream contentStream = new PDPageContentStream(pDDocument, page, PDPageContentStream.AppendMode.APPEND, true); // questo permette di sovrascrivere cio che ho nella pagina
            float startX = 100; // Posizione X di partenza posizione sulla riga
            float startY = 350; // Posizione Y di partenza posizione verso l'alto ( per scendere nel foglio abbassa il numero)

            float fieldOffsetX = 150; // Offset orizzontale per i campi partendo da startX
            float lineSpacing = 50; // Spazio verticale tra le righe
            // Seleziona il font comune per testo e campi
            PDType1Font commonFont = PDType1Font.HELVETICA;
            for (int i = 0; i < getPdfFieldDto.size(); i++) {

                float currentY = startY - (i * lineSpacing * 1); // Spazio tra le righe (2 linee per persona)
                // Testo statico: " da inserire "
                contentStream.setFont(commonFont, 11);
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, currentY);
                contentStream.showText("Tipo Prodotto ");
                contentStream.endText();
                // Campo per il nome
                PDTextField nameField = new PDTextField(pDAcroForm);
                nameField.setPartialName("tipo_prodotto" + i);
                // configurazione proprieta'
                nameField.setReadOnly(true);
                nameField.setValue(getPdfFieldDto.get(i).getTypeProduct().toLowerCase());
                nameField.setDefaultAppearance("/Helvetica 11 Tf 0 g"); // Imposta il font e la dimensione
                // aggiungi l'acroform al documento
                pDAcroForm.getFields().add(nameField);

                //Un "widget" è la rappresentazione visiva del campo sul PDF qua setti la configurazione come rettangolo e lo imposti
                PDAnnotationWidget nameWidget = nameField.getWidgets().get(0);
                PDRectangle nameRect = new PDRectangle(startX + fieldOffsetX, currentY - 5, 100, 20); // Posizione a metà riga
                nameWidget.setRectangle(nameRect);
                page.getAnnotations().add(nameWidget);

                // Testo statico: "Il prezzo è:"
                contentStream.beginText();
                contentStream.setFont(commonFont, 11);
                contentStream.newLineAtOffset(startX, currentY - lineSpacing + (lineSpacing / 2)); // spazio fra tipo prodotto e prezzo
                contentStream.showText("Prezzo ");
                contentStream.endText();
                // Campo per il prezzo
                PDTextField ageField = new PDTextField(pDAcroForm);
                ageField.setPartialName("prezzo_" + i);
                ageField.setReadOnly(true);
                ageField.setValue(getPdfFieldDto.get(i).getPriceProduct() + "Euro");
                ageField.setDefaultAppearance("/Helvetica 11 Tf 0 g"); // Imposta il font e la dimensione
                pDAcroForm.getFields().add(ageField);
                PDAnnotationWidget ageWidget = ageField.getWidgets().get(0);
                PDRectangle ageRect = new PDRectangle(startX + fieldOffsetX, currentY - lineSpacing + (lineSpacing / 2), 150, 20);
                ageWidget.setRectangle(ageRect);
                page.getAnnotations().add(ageWidget);
            }

            contentStream.close();
            pDDocument.save(operationSystem + "\\output.pdf");


            byte[] pdfBytes = saveDocumentInByteArray(pDDocument);
            savePdfToDatabase(pdfBytes, order.get(0));

            return pDDocument;

        } catch (IOException e) {
            throw new IOException();
        }
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


    private String getPathDesktop() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            String userProfile = System.getenv("USERPROFILE");
            return userProfile + "\\Desktop";
        } else if (osName.contains("mac")) {
            String userHome = System.getProperty("user.home");
            return userHome + "/Desktop";
        } else if (osName.contains("nix") || osName.contains("nux")) {
            String userHome = System.getProperty("user.home");
            return userHome + "/Desktop";
        } else {
            throw new UnsupportedOperationException("Operation system not supported");
        }
    }


    /**
     * @param pdDocument
     * @return byte[] this method return an array of byte with inside the entire document PDF, is important close here the document after saved the outputstream
     * @throws IOException
     */
    private byte[] saveDocumentInByteArray(PDDocument pdDocument) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdDocument.save(byteArrayOutputStream);
        pdDocument.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * @param pdfBytes
     * @param order    This method save into database a BLOB value to retrieve then in another one method the entire pdf
     */
    private void savePdfToDatabase(byte[] pdfBytes, Order order) {
        Optional<Pdf> pdf1 = Optional.ofNullable(pdfRepository.findByNumberOrder(order.getNumberOrder()));
        if (pdf1.isEmpty()) {
            Pdf pdf = Pdf.builder()
                    .pdfData(pdfBytes)
                    .numberOrder(order.getNumberOrder())
                    .build();
            pdfRepository.save(pdf);
        }else {
            pdf1.get().setPdfData(pdfBytes);
            pdfRepository.save(pdf1.get());
        }

    }

    private byte[] retrieveByteArrayFromDB(String order) {
        Pdf pdf = pdfRepository.findByNumberOrder(order);
        byte[] bytes = pdf.getPdfData();
        return bytes;
    }


    /**
     * This method retrieve the PDF from the database based of the array of bytes and build again the document
     */
    private PDDocument constructPdfFromDatabase(String numberOrder) throws IOException {
        // Recupera il byte array dal database
        byte[] pdfBytes = retrieveByteArrayFromDB(numberOrder);

        // Ricostruisce il documento PDF dall'array di byte
        PDDocument pdfDocument = PDDocument.load(pdfBytes);

        return pdfDocument; // Restituisce il documento PDF ricostruito
    }


    /**
     * @param numberOrder
     * @throws IOException based of the order passed to the method add another one page to the PDF saved into the database
     */
    public void processPdfFromDB(String numberOrder) throws IOException {
        String operationSystem = getPathDesktop();
        // Ricrea il documento pdf
        PDDocument pdfDocument = constructPdfFromDatabase(numberOrder);
        // aggiungo una pagina per facs simile elaborazione dal ristorante

        PDPage newPage = new PDPage(); // Aggiungi una nuova pagina
        pdfDocument.addPage(newPage);
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, newPage);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 11);
        contentStream.newLineAtOffset(100, 650);
        contentStream.showText("Grazie per l'acquisto il tuo ordine è stato processato");
        contentStream.endText();
        contentStream.close();
        // Salva il PDF su disco
        pdfDocument.save(operationSystem + "\\output1.pdf");

        byte[] pdfBytes = saveDocumentInByteArray(pdfDocument);

        Pdf pdf = pdfRepository.findByNumberOrder(numberOrder);
        pdf.setPdfData(pdfBytes);
        pdf.setDateSaved(LocalDate.now());
        pdf.setDocumentProcessed(true);
        pdfRepository.save(pdf);

    }

    /**
     * This method allow to retrieve all order unprocessed from the database
     */
    public List<UnprocessedOrderDTO> retrieveUnprocessedOrder(){
        List<UnprocessedOrderDTO> listOrderUnprocessed =pdfRepository.findUnprocessedOrders();
        return listOrderUnprocessed;
    }

    //  TODO: AGGIUNGERE DUE RIGHE CON SOMMA TOTALE PRODOTTI LEGGERE L'ACROFORM DEI VALORI E FARE LA SOMMA
    //TODO: AGGIUNGERE METODO PER OGNI CUSTOMER IL RECAP DEI SUOI ORDINI
    // TODO: AGGIUNGERE TABELLA TESSERA PUNTI, CONTERA' IN BASE AL NUMERO DI ORDINE QUANTI PRODOTTI HA PRESO PER OGNI PRODOTTO 1 PUNTO
    //TODO: AGGIUNGERE UN ACROFORM SOMMA SPESA: E QUESTO VERRA' NEL CASO SCONTATO AL RAGGIUNGIMENTO DI 10 PUNTI CON UNO SCONTO PREVISTO
}
