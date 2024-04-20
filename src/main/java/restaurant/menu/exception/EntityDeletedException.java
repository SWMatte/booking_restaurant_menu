package restaurant.menu.exception;



public class EntityDeletedException extends Exception  {
    public EntityDeletedException() {
        super();
    }
    public EntityDeletedException(String message) {
        super(message);
    }

    public EntityDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
