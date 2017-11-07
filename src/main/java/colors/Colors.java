package colors;

public enum Colors {

    ANSI_RED("\u001B[31m"),
    ANSI_YELLOW("\u001b[33m"),
    ANSI_RESET("\u001B[0m"),
    ANSI_GREEN("\u001B[32m");

    private final String ansi;

    Colors(String ansi) {
        this.ansi = ansi;
    }

    public String toString() {
        return this.ansi;
    }
}
