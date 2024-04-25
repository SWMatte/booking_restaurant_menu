package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.common.Utils;
import restaurant.menu.entities.Product;
import restaurant.menu.entities.User;
import restaurant.menu.exception.CustomEntityNotFoundException;
import restaurant.menu.exception.EntityDeletedException;
import restaurant.menu.repository.ProductRepository;
import restaurant.menu.repository.UserRepository;
import restaurant.menu.service.CrudOperation;

@Service
@Slf4j
public class ProductService implements CrudOperation<Product> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addElement(Product element) throws Exception {
        log.info("Enter into {}, start method: addElement", Product.class);
        try {
            User user = userRepository.findById(element.getUser().getIdUser()).orElseThrow(EntityNotFoundException::new);
            if (!user.isDeleteFlag()) {
                element.setUser(user);
                element.setNumberItem(Utils.getUUID());
                productRepository.save(element);
                log.info("Finished  method: addElement");
            } else {
                throw new EntityDeletedException();
            }
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity User with ID {}, stack error: {}", Product.class, element.getUser().getIdUser(), e.getMessage());
            throw new CustomEntityNotFoundException("Not found entity User with ID");
        } catch (EntityDeletedException e){
            throw new EntityDeletedException("Entity deleted from database");
        }

    }

    @Override
    public void updateElement(Product element) {
        log.info("Enter into {}, start method: updateElement", Product.class);
        try {
            Product productDB = productRepository.findById(element.getIdProduct()).orElseThrow(() -> new EntityNotFoundException());
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
            productRepository.save(productDB);
            log.info("Finished  method: updateElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Product with ID {}, stack error:{}", Product.class, element.getIdProduct(), e.getMessage());
        }

    }

    @Override
    public void deleteElement(int id) {
        log.info("Enter into {}, start method: deleteElement", Product.class);
        try {
            productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
            productRepository.deleteById(id);
            log.info("Finished  method: addElement");
        } catch (EntityNotFoundException e) {
            log.error("Error into {}, not found entity Product with ID {}, stack error:{}", Product.class, id, e.getMessage());
        }


    }
}
