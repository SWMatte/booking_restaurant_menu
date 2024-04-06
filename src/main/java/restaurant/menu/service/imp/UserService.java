package restaurant.menu.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.menu.entities.User;
import restaurant.menu.repository.UserRepository;
import restaurant.menu.service.CrudOperation;

@Service
public class UserService implements CrudOperation<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addElement(User element) {

    }

    @Override
    public void updateElement(User element) {

    }

    @Override
    public void deleteElement(int id) {

    }
}
