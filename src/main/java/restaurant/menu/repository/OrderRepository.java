package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
