package table;

import exceptions.MissingColumn;
import exceptions.MissingHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Table {

    private final Header header;
    private final List<DataRow> rows;
    private int width;

    public Table(Header header) {
        this.header = Optional.ofNullable(header).orElseThrow(() -> new MissingHeader("Table must have a header"));
        this.width = header.getWidth();
        this.rows = new ArrayList<>();
    }

    /**
     * Adds a row to a table.
     *
     * Note that the table will not accept data that has missing
     * columns (map's keys). However, it lenient with regards to
     * columns that do not exist in the header.
     *
     * @param rowColumns data to be stored in the table.
     */
    public void addRow(Map<String, String> rowColumns) {
        requireNotEmpty();
        requireSameSize(rowColumns);
        containsHeaderColumns(rowColumns);

        rows.add(new DataRow(rowColumns));
    }

    private void requireNotEmpty() {
        if (getHeaderColumns().isEmpty()) {
            throw new MissingHeader("Table must have a header with populated data");
        }
    }

    private void requireSameSize(Map<String, String> rowColumns) {
        if (getHeaderColumns().size() > rowColumns.size()) {
            throw new MissingColumn("The row has less columns than the header");
        }
    }

    private void containsHeaderColumns(Map<String, String> rowColumns) {
        Map<String, Integer> headerColumns = getHeaderColumns();
        headerColumns.forEach((column, width) -> {
            if (!rowColumns.containsKey(column)) {
                throw new MissingColumn("Column ['" + column + "'] is missing from " + DataRow.class.getSimpleName());
            }
        });
    }

    public Map<String, Integer> getHeaderColumns() {
        return header.getColumns();
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public int getWidth() {
        return width;
    }
}
