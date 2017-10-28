package utils;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

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

    public static String padRight(String string, int minimumLength, char padChar) {
        requireNonNull(string);

        if (string.length() >= minimumLength) {
             return string;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(string);
        for (int i = string.length(); i < minimumLength; i++) {
            builder.append(padChar);
        }

        return builder.toString();
    }

    public static String padLeft(String string, int minimumLength, char padChar) {
        requireNonNull(string);

        if (string.length() >= minimumLength) {
            return string;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = string.length(); i < minimumLength; i++) {
            builder.append(padChar);
        }
        builder.append(string);

        return builder.toString();
    }
}
