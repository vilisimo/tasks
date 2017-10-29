package utils;

import exceptions.EmptyCollection;

import java.util.Collection;
import java.util.Map;

public final class Validations {

    private Validations() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static <T, V> void requireNonEmpty(Map<T, V> map, String message) {
        if (map.isEmpty()) {
            throw new EmptyCollection(message);
        }
    }

    public static void requireNonEmpty(Collection collection, String message) {
        if (collection.isEmpty()) {
            throw new EmptyCollection(message);
        }
    }

    public static void requireLarger(int minimum, int actual, String message) {
        if (actual < minimum) {
            throw new IllegalArgumentException(message);
        }
    }
}
