package restaurant.menu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restaurant.menu.repository.OrderRepository;
import restaurant.menu.repository.PdfRepository;


@Service
public class PdfService   {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private OrderRepository orderRepository;


}
