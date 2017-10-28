package utils;

import exceptions.EmptyCollection;

import java.util.Map;

public final class Validations {

    private Validations() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static <T, V> void requireNonEmpty(Map<T, V> collection, String message) {
        if (collection.isEmpty()) {
            throw new EmptyCollection(message);
        }
    }
}
