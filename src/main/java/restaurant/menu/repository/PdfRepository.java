package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.Pdf;

public interface PdfRepository extends JpaRepository<Pdf, Integer> {

    Pdf findByNumberOrder(String order);
}
