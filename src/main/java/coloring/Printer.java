package coloring;

import static coloring.Colors.ANSI_RED;
import static coloring.Colors.ANSI_RESET;

public class Printer {

    public static void error(String errorText) {
        System.out.println(ANSI_RED + errorText + ANSI_RESET);
    }
}
