package printing;

import table.DataRow;
import table.Header;
import table.Table;
import utils.Containers;
import utils.Strings;

import java.util.*;
import java.util.stream.Collectors;

import static utils.Validations.requireLarger;

public class TablePrinter {

    private final Table table;
    private String fancyBorder;
    private String simpleBorder;
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
        List<DataRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            printRow(rows.get(i));
            
            if (i == rows.size() - 1) {
                printFancyBorder();
            } else {
                printSimpleBorder();
            }
        }
    }

    private void printFancyBorder() {
        if (fancyBorder == null) {
            designFancyBorder();
        }
        System.out.println(fancyBorder);
    }

    private void printSimpleBorder() {
        if (simpleBorder == null) {
            designSimpleBorder();
        }
        System.out.println(simpleBorder);
    }

    private void designFancyBorder() {
        int whitespacePadding = (cellPadding * table.columnCount()) * 2;
        int borderPadding = table.columnCount() + 1;
        int totalWidth = table.getWidth() + whitespacePadding + borderPadding;
        int cornerWidth = 2;
        String horizontalFiller = String.join("", Collections.nCopies(totalWidth - cornerWidth, "-"));
        this.fancyBorder = "+" + horizontalFiller + "+";
    }

    private void designSimpleBorder() {
        int whitespacePadding = (cellPadding * table.columnCount()) * 2;
        int borderPadding = table.columnCount() + 1;
        int totalWidth = table.getWidth() + whitespacePadding + borderPadding;
        this.simpleBorder = String.join("", Collections.nCopies(totalWidth, "-"));
    }

    private void printHeaderRow() {
        Header header = table.getHeader();
        List<String> columns = header.getColumns();

        List<List<String>> formattedColumns = new ArrayList<>();
        for (String column : columns) {
            formattedColumns.add(Strings.chopString(column, header.getColumnWidth(column)));
        }

        Containers.normalizeListSizes(formattedColumns, "\n");
        List<String> interleaved = Containers.interleave(formattedColumns, formattedColumns.get(0).size());
        List<List<String>> split = Containers.split(interleaved, interleaved.size() / header.columnCount());

        for (List<String> list : split) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String row = list.get(i);
                int width = header.getColumnWidth(columns.get(i));
                result.add(designColumnString(row, width));
            }

            String rowRepresentation = result
                    .stream()
                    .collect(Collectors.joining("|", "|", "|"));

            System.out.println(rowRepresentation);
        }
    }

    private void printRow(DataRow row) {
        Header header = table.getHeader();
        List<String> names = row.getColumnNames();

        List<String> columns = new ArrayList<>();
        for (String name : names) {
            columns.add(row.getColumnValue(name));
        }

        List<List<String>> formattedColumns = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            formattedColumns.add(Strings.chopString(columns.get(i), header.getColumnWidth(names.get(i))));
        }

        Containers.normalizeListSizes(formattedColumns, "\n");
        List<String> interleaved = Containers.interleave(formattedColumns, formattedColumns.get(0).size());
        List<List<String>> split = Containers.split(interleaved, interleaved.size() / header.columnCount());

        for (List<String> list : split) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String r = list.get(i);
                int width = header.getColumnWidth(names.get(i));
                result.add(designColumnString(r, width));
            }

            String rowRepresentation = result
                    .stream()
                    .collect(Collectors.joining("|", "|", "|"));

            System.out.println(rowRepresentation);
        }
    }

    private String designColumnString(String column, int size) {
        StringBuilder builder = new StringBuilder();

        if (column.contains("\n")) {
            column = column.replace("\n", "");
        }

        builder.append(Strings.repeat(' ', cellPadding));
        builder.append(column);
        builder.append(Strings.repeat(' ', size - column.length()));
        builder.append(Strings.repeat(' ', cellPadding));

        return builder.toString();
        // String result = builder.toString();
        // if (result.length() >= 3 && result.charAt(1) == ' ') {
        //     result = result.substring(0, 1) + result.substring(2, result.length()) + " ";
        // }
        // return result;
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
        int titleLength = 4;
        int deadlineLength = "2018-01-11".length();
        int descriptionLength = usableWidth - titleLength - deadlineLength - deadlineLength;

        Map<String, Integer> headerColumns = new LinkedHashMap<>();
        headerColumns.put("Title", titleLength);
        headerColumns.put("Description", descriptionLength);
        headerColumns.put("Created", deadlineLength);
        headerColumns.put("Deadline", deadlineLength);
        Header header = new Header(headerColumns);

        Map<String, String> rowColumns = new LinkedHashMap<>();
        rowColumns.put("Title", "test1");
        rowColumns.put("Description", "some really long description that has no way of fitting in one line and perhaps even not fitting on two lines or even three");
        rowColumns.put("Created", "2017-11-11");
        rowColumns.put("Deadline", "2018-05-11");
        DataRow row = new DataRow(rowColumns);

        Map<String, String> rowColumns2 = new LinkedHashMap<>();
        rowColumns2.put("Title", "test2");
        rowColumns2.put("Description", "Shorter description that definitely fits on two lines");
        rowColumns2.put("Created", "2011-11-11");
        rowColumns2.put("Deadline", "2015-01-10");
        DataRow row2 = new DataRow(rowColumns2);

        Table table = new Table(header);
        table.addRow(row);
        table.addRow(row2);
        TablePrinter printer = new TablePrinter(table);
        printer.printTable();
    }
}
