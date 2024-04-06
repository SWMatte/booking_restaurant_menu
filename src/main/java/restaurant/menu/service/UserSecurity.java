package restaurant.menu.service;

import restaurant.menu.entities.User;

import java.util.Optional;

public interface UserSecurity {
    User findById(int id);

    Optional<User> findByEmail(String email);

    User loadByUserId(String id);
}
