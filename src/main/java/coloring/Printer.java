package coloring;

import entities.Task;
import utils.Chronos;

import java.util.List;

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

    public static void printTasks(List<Task> tasks) {
        tasks.forEach(task -> Printer.success(
                "Task " + task.getId() + ":"
                        + "\n" + task.getDescription()
                        + "\nCreated: " + Chronos.instantToLocalDate(task.getCreated())
                        + "\nDeadline: " + Chronos.instantToLocalDate(task.getDeadline())
                        + "\n")
        );
    }
}
