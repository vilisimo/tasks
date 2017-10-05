package colouring;

import static colouring.Colors.ANSI_RED;
import static colouring.Colors.ANSI_RESET;

public class Printer {

    public static void printRed(String toColour) {
        System.out.println(ANSI_RED + toColour + ANSI_RESET);
    }
}
