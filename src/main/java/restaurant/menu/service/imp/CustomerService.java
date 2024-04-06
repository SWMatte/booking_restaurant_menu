package restaurant.menu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.entities.Customer;
import restaurant.menu.repository.CustomerRepository;
import restaurant.menu.service.CrudOperation;

@Service
public class CustomerService implements CrudOperation<Customer> {
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void addElement(Customer element) {

    }

    @Override
    public void updateElement(Customer element) {

    }

    @Override
    public void deleteElement(int id) {

    }
}
