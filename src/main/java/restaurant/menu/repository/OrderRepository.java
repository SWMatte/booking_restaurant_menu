package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * This query allow to retrieve the User entity from email customer from the Order:
     * Order has inside product -> product has inside user -> User allow to retrieve name and last name for customer
     */
    String EMAIL_USER_BY_PRODUCT = "" +
            "SELECT u " +
            "FROM Order o " +
            "INNER JOIN o.product p " +
            "INNER JOIN p.user u " +
            "WHERE o.emailCustomer = :emailCustomer";

    @Query(EMAIL_USER_BY_PRODUCT)
    User getUserByEmailCustomerFromOrder(@Param("emailCustomer") String emailCustomer);





    List<Order> findByNumberOrder(String numberOrder);


}
