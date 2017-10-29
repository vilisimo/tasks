package utils;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static utils.Validations.requireLarger;

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
        requireLarger(0, times, "'" + character + "' cannot be repeated " + times + " times");

        if (times == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(character);
        }

        return builder.toString();
    }

    public static void chopString(String string, int size, List<String> result) {
        requireNonNull(result, "Result list should not be null");
        requireLarger(1, size, "Size should not be larger than 1 [current=" + size + "]");
        requireNonNull(string, "String to be chopped should not be null");

        if (string.length() > size) {
            result.add(string.substring(0, size));
            chopString("\n" + string.substring(size), size, result);
        } else {
            result.add(string);
        }
    }
}
