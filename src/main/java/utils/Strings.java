package utils;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static utils.Validations.requireNonNegative;

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

        return string + repeat(padChar, minimumLength - string.length());
    }

    public static String padLeft(String string, int minimumLength, char padChar) {
        requireNonNull(string);

        if (string.length() >= minimumLength) {
            return string;
        }

        return repeat(padChar, minimumLength - string.length()) + string;
    }

    public static String repeat(char character, int times) {
        requireNonNegative(times, "'" + character + "' cannot be repeated " + times + " times");

        if (times == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(character);
        }

        return builder.toString();
    }
}
