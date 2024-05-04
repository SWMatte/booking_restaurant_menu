package restaurant.menu.service;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public interface PdfOperation {

    PDDocument createPdf(String numberOrder) throws IOException;
    void processPdfFromDB(String  numberOrder)throws IOException;

}
