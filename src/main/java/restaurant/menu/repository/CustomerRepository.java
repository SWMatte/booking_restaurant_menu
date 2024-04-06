package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.menu.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
