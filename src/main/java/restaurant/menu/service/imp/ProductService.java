package restaurant.menu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.entities.Product;
import restaurant.menu.repository.ProdcutRepository;
import restaurant.menu.service.CrudOperation;

@Service
public class ProductService implements CrudOperation<Product> {

    @Autowired
    private ProdcutRepository prodcutRepository;

    @Override
    public void addElement(Product element) {
        
    }

    @Override
    public void updateElement(Product element) {

    }

    @Override
    public void deleteElement(int id) {

    }
}
