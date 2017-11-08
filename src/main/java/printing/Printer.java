package printing;

import entities.Task;
import table.DataRow;
import table.Header;
import table.Table;
import utils.Chronos;

import java.util.LinkedHashMap;
import java.util.List;

import static colors.Colors.*;
import static java.util.Objects.requireNonNull;

/**
 * A class that is concerned with printing: from errors
 * to tables.
 */
public final class Printer {

    private static final String PREFIX = "TASKS :: ";

    private Printer() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static void success(String successText) {
        System.out.println(ANSI_GREEN + PREFIX + successText + ANSI_RESET);
    }

    public static void error(String errorText) {
        System.out.println(ANSI_RED + PREFIX + errorText + ANSI_RESET);
    }

    public static void warning(String warningText) {
        System.out.println(ANSI_YELLOW + PREFIX + warningText + ANSI_RESET);
    }

    public static void printTasks(List<Task> tasks) {
        requireNonNull(tasks, "Task list should not be null");
        if (tasks.isEmpty()) {
            return;
        }

        int availableSpace = TablePrinter.usableWidth(79, 1, 4);
        int taskWidth = "task".length();
        int dateWidth = "2017-01-01".length();
        int categoryWidth = "category".length();
        int descriptionWidth = availableSpace - taskWidth - dateWidth - categoryWidth;

        LinkedHashMap<String, Integer> columns = new LinkedHashMap<>();
        columns.put("Task", taskWidth);
        columns.put("Description", descriptionWidth);
        columns.put("Category", categoryWidth);
        columns.put("Deadline", dateWidth);
        Header header = new Header(columns);
        Table table = new Table(header);

        tasks.forEach(task -> table.addRow(createRow(task)));

        TablePrinter printer = new TablePrinter(table);
        printer.printTable();
    }

    private static DataRow createRow(Task task) {
        LinkedHashMap<String, String> row = new LinkedHashMap<>();
        row.put("Task", String.valueOf(task.getId()));
        row.put("Description", task.getDescription());
        row.put("Category", task.getCategory());
        row.put("Deadline", Chronos.instantToLocalDate(task.getDeadline()));

        return new DataRow(row);
    }
}
