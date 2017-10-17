package coloring;

import static coloring.Colors.*;

public final class Printer {

    private Printer() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static void success(String successText) {
        System.out.println(ANSI_GREEN + successText + ANSI_RESET);
    }

    public static void error(String errorText) {
        System.out.println(ANSI_RED + errorText + ANSI_RESET);
    }

    public static void warning(String warningText) {
        System.out.println(ANSI_YELLOW + warningText + ANSI_RESET);
    }
}
