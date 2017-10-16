package coloring;

import static coloring.Colors.*;

public class Printer {

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
