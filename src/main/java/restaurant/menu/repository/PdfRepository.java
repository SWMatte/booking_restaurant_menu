package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.Pdf;
import restaurant.menu.entities.dto.UnprocessedOrderDTO;

import java.util.List;

public interface PdfRepository extends JpaRepository<Pdf, Integer> {

    Pdf findByNumberOrder(String order);

    @Query("select new restaurant.menu.entities.dto.UnprocessedOrderDTO(p.numberOrder) from Pdf p where p.documentProcessed = false")
    List<UnprocessedOrderDTO> findUnprocessedOrders();

}
