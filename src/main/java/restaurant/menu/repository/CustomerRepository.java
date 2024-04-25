package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.User;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerByUser(User user);
}

