package restaurant.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Product;

public interface ProdcutRepository extends JpaRepository<Product, Integer> {
}
