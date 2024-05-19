package restaurant.menu.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import restaurant.menu.entities.dto.UnprocessedOrderDTO;

import java.io.IOException;
import java.util.List;

public interface PdfOperation {

    PDDocument createPdf(String numberOrder) throws IOException;
    void processPdfFromDB(String  numberOrder)throws IOException;

    List<UnprocessedOrderDTO> retrieveUnprocessedOrder();

}
