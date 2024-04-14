package restaurant.menu.service.imp;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.menu.common.Utils;
import restaurant.menu.entities.User;
import restaurant.menu.exception.CustomEntityNotFoundException;
import restaurant.menu.repository.UserRepository;
import restaurant.menu.service.CrudOperation;
import restaurant.menu.service.UserSecurity;

import java.util.Optional;

@Service
@Slf4j
public class UserService implements  UserSecurity {

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
    public void updateElement(String email) {
        try {
            User user= userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException());
            user.setEmail(email);
            userRepository.save(user);
        }catch (EntityNotFoundException e){
            log.error("Entity with email : {} not available in repository", email);
            throw new CustomEntityNotFoundException("Entity not available");
        }
    }

    /* TODO: fare un metodo per il cambio password con password encoder:
    *   1) fai passare la vecchia passwrod e la nuova
    *   2) se matcha aggiorna la password
    *   3 ) restituisci messaggio password aggiornata
    * */



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
        return null;
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
