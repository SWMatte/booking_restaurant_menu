package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restaurant.menu.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    String EMAIL = "SELECT o.product.user.email FROM Order o WHERE o.product.idProduct = :productId";

    @Query(EMAIL)
    String getEmailByProductId(@Param("productId") int productId);

}
