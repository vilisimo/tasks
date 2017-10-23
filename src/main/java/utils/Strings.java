package utils;

import java.util.Optional;

public final class Strings {

    private Strings() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static Optional<String> joinStrings(String[] strings) {
        if (strings != null) {
            return Optional.of(String.join(" ", strings));
        }

        return Optional.empty();
    }
}
