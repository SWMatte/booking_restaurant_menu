package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.menu.common.Utils;
import restaurant.menu.entities.User;
import restaurant.menu.repository.UserRepository;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.UserSecurity;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements CrudOperation<User>, UserSecurity {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void addElement(User element) {
        log.info("Start method addElement {}", UserService.class);
        try {
            if (!Utils.nullElement(element)) {
                userRepository.save(element);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            log.error("{} value is null", element);
        }


    }

    @Override
    public void updateElement(User element) {

    }

    @Override
    public void deleteElement(int id) {

    }

    @Override
    public User findById(int id) {
        log.info("Start method findById {}", UserService.class);
        User user;
        try {
            return user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        } catch (EntityNotFoundException e) {
            log.error("Entity with id : {} not available in repository", id);
            user = null;
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Start method findByEmail {}", UserService.class);
        try {
            return userRepository.findByEmail(email);
        } catch (EntityNotFoundException e) {
            log.error("Entity with email: {} not available in repository", email);
            return Optional.empty();
        }
    }


    @Override
    public User loadByUserId(String id) {
        try {
            User user = userRepository.findById(Integer.valueOf(id)).orElseThrow(EntityNotFoundException::new);
            return user;
        } catch (EntityNotFoundException e) {
            log.error("Entity with id : {} not available in repository", id);
            return null;
        }
    }
}
