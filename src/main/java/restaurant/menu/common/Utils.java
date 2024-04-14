package restaurant.menu.common;

import java.util.UUID;

/**
 * This class is born to common methods
 */
public class Utils {


    public static final <T> boolean nullElement(T element) {
        return element == null || element.toString().isEmpty();
    }

    public static final String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
