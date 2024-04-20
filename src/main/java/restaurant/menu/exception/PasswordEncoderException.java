package restaurant.menu.exception;



public class PasswordEncoderException extends Exception  {
    public PasswordEncoderException() {
        super();
    }
    public PasswordEncoderException(String message) {
        super(message);
    }

    public PasswordEncoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
