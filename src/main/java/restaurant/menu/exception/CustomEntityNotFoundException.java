package restaurant.menu.exception;

import jakarta.persistence.EntityNotFoundException;

public class CustomEntityNotFoundException extends EntityNotFoundException {
    public CustomEntityNotFoundException(String message) {
        super(message);
    }
    public CustomEntityNotFoundException(String message, Throwable cause) {
        super(message, (Exception) cause);
    }
}
