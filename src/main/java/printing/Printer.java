package printing;

import entities.Task;
import table.DataRow;
import table.Header;
import table.Table;
import utils.Chronos;

import java.util.LinkedHashMap;
import java.util.List;

import static coloring.Colors.*;
import static java.util.Objects.requireNonNull;

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

    public static void printTasks(List<Task> tasks) {
        requireNonNull(tasks, "Task list should not be null");

        tasks.forEach(task -> Printer.success(
                "Task " + task.getId() + ":"
                        + "\n" + task.getDescription()
                        + "\nCreated: " + Chronos.instantToLocalDate(task.getCreated())
                        + "\nDeadline: " + Chronos.instantToLocalDate(task.getDeadline())
                        + "\n")
        );
    }
}
