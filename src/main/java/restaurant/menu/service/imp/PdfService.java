package restaurant.menu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.entities.Pdf;

import restaurant.menu.repository.PdfRepository;
import restaurant.menu.service.CrudOperation;

@Service
public class PdfService implements CrudOperation<Pdf> {

    @Autowired
    private PdfRepository pdfRepository;

    @Override
    public void addElement(Pdf element) {
        
    }

    @Override
    public void updateElement(Pdf element) {

    }

    @Override
    public void deleteElement(int id) {

    }
}
