package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.common.Utils;
import restaurant.menu.entities.Order;
import restaurant.menu.entities.Product;
import restaurant.menu.entities.dto.OrderRequestDTO;
import restaurant.menu.repository.OrderRepository;
import restaurant.menu.repository.ProductRepository;
import restaurant.menu.service.OrderOperation;

import java.time.LocalDate;

@Service
@Slf4j
public class OrderService implements OrderOperation {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;



    @Override
    public void addOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Enter into {}, start method: addOrder", Order.class);
        String uuid= Utils.getUUID();
        try {
            if (!Utils.nullElement(orderRequestDTO)) {
                for (Integer id : orderRequestDTO.getIdProduct()) {
                    Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
                    Order order = Order.builder()
                            .product(product)
                            .numberOrder(uuid)
                            .addressCustomer(orderRequestDTO.getAddress())
                            .emailCustomer(orderRequestDTO.getEmailUser())
                            .dateOrder(LocalDate.now())
                            .build();
                    orderRepository.save(order);
                }


                log.info("Finished  method: addOrder");
            }
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Product with ID {}, stack error:{}", Order.class, orderRequestDTO.getIdProduct(), e.getMessage());
        }
    }


}
