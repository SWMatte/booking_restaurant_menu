package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
