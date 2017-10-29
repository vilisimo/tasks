package printing;

import table.Header;
import table.Table;
import utils.Strings;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static utils.Validations.requireLarger;

public class TablePrinter {

    private final Table table;
    private String fancyBorder;
    private final int cellPadding;

    public TablePrinter(Table table) {
        this.cellPadding = 1;
        this.table = table;
    }

    public void printTable() {
        printHeader();
    }

    private void printHeader() {
        printFancyBorder();
        printHeaderRow();
        printFancyBorder();
    }

    private void printFancyBorder() {
        if (fancyBorder == null) {
            designFancyBorder();
        }
        System.out.println(fancyBorder);
    }

    private void designFancyBorder() {
        int whitespacePadding = (cellPadding * table.columnCount()) * 2;
        int borderPadding = table.columnCount() + 1;
        int totalWidth = table.getWidth() + whitespacePadding + borderPadding;
        int cornerWidth = 2;
        String horizontalFiller = String.join("", Collections.nCopies(totalWidth - cornerWidth, "-"));
        this.fancyBorder = "+" + horizontalFiller + "+";
    }

    private void printHeaderRow() {
        Header header = table.getHeader();
        List<String> columns = header.getColumns();
        String rowRepresentation = columns
                .stream()
                .map(str -> designColumnString(str, header.getColumnWidth(str)))
                .collect(Collectors.joining("|", "|", "|"));

        System.out.println(rowRepresentation);
    }

    private String designColumnString(String column, int size) {
        StringBuilder builder = new StringBuilder();

        builder.append(Strings.repeat(' ', cellPadding));
        builder.append(column);
        builder.append(Strings.repeat(' ', size - column.length()));
        builder.append(Strings.repeat(' ', cellPadding));

        return builder.toString();
    }

    public static int usableWidth(int tableWidth, int singlePadWidth, int columns) {
        requireLarger(0, singlePadWidth, "Width of the padding cannot be negative [entered=" + singlePadWidth + "]");
        requireLarger(1, tableWidth, "Table width cannot be less than 1 [entered=" + tableWidth + "]");
        requireLarger(1, columns, "Table cannot have less columns than 1 [entered=" + columns + "]");

        int bordersWidth = columns + 1;
        int totalPaddingWidth = (singlePadWidth * 2) * columns;
        int usableWidth = tableWidth - bordersWidth - totalPaddingWidth;

        if (usableWidth < 0) {
            return 0;
        }

        return  usableWidth;
    }

    public static void main(String[] args) {
        int usableWidth = TablePrinter.usableWidth(79, 1, 3);
        int titleLength = "title".length();
        int deadlineLength = "deadline".length();
        int descriptionLength = usableWidth - titleLength - deadlineLength;
        Map<String, Integer> columns = new LinkedHashMap<>();
        columns.put("Title", titleLength);
        columns.put("Description", descriptionLength);
        columns.put("Deadline", deadlineLength);
        Header header = new Header(columns);
        TablePrinter printer = new TablePrinter(new Table(header));
        printer.printTable();
    }
}
