package restaurant.menu.service;

import restaurant.menu.entities.dto.OrderRequestDTO;

public interface OrderOperation {

    void addOrder(OrderRequestDTO orderRequestDTO);
}
