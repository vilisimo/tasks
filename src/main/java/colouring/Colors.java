package colouring;

public enum Colors {

    ANSI_RED("\u001B[31m"),
    ANSI_RESET("\u001B[0m");

    private final String ansi;

    Colors(String ansi) {
        this.ansi = ansi;
    }

    public String toString() {
        return this.ansi;
    }
}
