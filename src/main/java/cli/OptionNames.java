package cli;

public enum OptionNames {
    ADD("add", "a"),
    DEADLINE("deadline", "d"),
    CATEGORY("category", "cat"),

    FILTER("filter", "f"),

    REMOVE("remove", "rm"),
    CLEAR("clear"),

    HELP("help", "h");

    private final String longOpt;
    private final String shortOpt;

    OptionNames(String longOpt, String shortOpt) {
        this.longOpt = longOpt;
        this.shortOpt = shortOpt;
    }

    OptionNames(String longOpt) {
        this.longOpt = longOpt;
        this.shortOpt = longOpt;
    }

    public String longOpt() {
        return longOpt;
    }

    public String shortOpt() {
        return shortOpt;
    }
}
