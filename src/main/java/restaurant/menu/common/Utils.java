package restaurant.menu.common;

/**
 * This class is born to common methods
 */
public class Utils {


    public static final <T> boolean nullElement(T element) {
        return element == null || element.toString().isEmpty();
    }
}
