package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {




    List<Order> findByNumberOrder(String numberOrder);


    @Procedure(value = "customer_numbers_orders",outputParameterName = "number")
    int getNumberOrders(String email);
}
