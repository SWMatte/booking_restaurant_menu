package restaurant.menu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.entities.Order;
import restaurant.menu.repository.OrderRepository;
import restaurant.menu.service.CrudOperation;

@Service
public class OrderService implements CrudOperation<Order> {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addElement(Order element) {
        
    }

    @Override
    public void updateElement(Order element) {

    }

    @Override
    public void deleteElement(int id) {

    }
}
