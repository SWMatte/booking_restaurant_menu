package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.common.Utils;
import restaurant.menu.entities.Customer;
import restaurant.menu.entities.Product;
import restaurant.menu.repository.CustomerRepository;
import restaurant.menu.service.CrudOperation;

@Service
@Slf4j
public class CustomerService implements CrudOperation<Customer> {
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void addElement(Customer element) {
        log.info("Enter into {}, start method: addElement", Customer.class);
        try {
            if (!Utils.nullElement(element))
                customerRepository.save(element);
            else
                throw new NullPointerException();

        } catch (NullPointerException e) {
            log.error("Error on method add customer");
        }
    }

    @Override
    public void updateElement(Customer element) {
        log.info("Enter into {}, start method: updateElement", Customer.class);
        try {
            Customer customerDB = customerRepository.findById(element.getIdCustomer()).orElseThrow(() -> new EntityNotFoundException());
            if (!Utils.nullElement(element.getName())) {
                customerDB.setName(element.getName());
            }
            if (!Utils.nullElement(element.getAddress())) {
                customerDB.setAddress(element.getAddress());
            }
            if (!Utils.nullElement(element.getLastName())) {
                customerDB.setName(element.getLastName());
            }
            if (!Utils.nullElement(element.getPhoneNumber())) {
                customerDB.setPhoneNumber(element.getPhoneNumber());
            }
            customerRepository.save(customerDB);
            log.info("Finished  method: updateElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Customer with ID {}, stack error:{}", Customer.class, element.getIdCustomer(), e.getMessage());
        }

    }

    @Override
    public void deleteElement(int id) {
        log.info("Enter into {}, start method: deleteElement", Customer.class);
        try {
            customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
            customerRepository.deleteById(id);
            log.info("Finished  method: addElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Customer with ID {}, stack error:{}", Customer.class, id, e.getMessage());
        }
    }
}
