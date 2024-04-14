package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.common.Utils;
import restaurant.menu.entities.Product;
import restaurant.menu.entities.User;
import restaurant.menu.repository.ProdcutRepository;
import restaurant.menu.repository.UserRepository;
import restaurant.menu.service.CrudOperation;

@Service
@Slf4j
public class ProductService implements CrudOperation<Product> {

    @Autowired
    private ProdcutRepository prodcutRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addElement(Product element) {
        log.info("Enter into {}, start method: addElement", Product.class);
        try {
            User user = userRepository.findById(element.getUser().getIdUser()).orElseThrow(() -> new EntityNotFoundException());
            element.setUser(user);
            element.setNumberItem(Utils.getUUID());
            prodcutRepository.save(element);
            log.info("Finished  method: addElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity User with ID {}, stack error: {}", Product.class, element.getUser().getIdUser(), e.getMessage());
        }

    }

    @Override
    public void updateElement(Product element) {
        log.info("Enter into {}, start method: updateElement", Product.class);
        try {
            Product productDB = prodcutRepository.findById(element.getIdProduct()).orElseThrow(() -> new EntityNotFoundException());
            if (!Utils.nullElement(element.getNameProduct())) {
                productDB.setNameProduct(element.getNameProduct());
            }
            if (!Utils.nullElement(element.getImageProduct())) {
                productDB.setImageProduct(element.getImageProduct());
            }
            if (!Utils.nullElement(element.getPrice())) {
                productDB.setPrice(element.getPrice());
            }
            if (!Utils.nullElement(element.getType())) {
                productDB.setType(element.getType());
            }
            prodcutRepository.save(productDB);
            log.info("Finished  method: updateElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Product with ID {}, stack error:{}", Product.class, element.getIdProduct(), e.getMessage());
        }

    }

    @Override
    public void deleteElement(int id) {
        log.info("Enter into {}, start method: deleteElement", Product.class);
        try {
            prodcutRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
            prodcutRepository.deleteById(id);
            log.info("Finished  method: addElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Product with ID {}, stack error:{}", Product.class, id, e.getMessage());
        }


    }
}
