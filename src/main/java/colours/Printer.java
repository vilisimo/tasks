package colours;

public class Printer {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void printRed(String toColour) {
        System.out.println(ANSI_RED + toColour + ANSI_RESET);
    }
}
