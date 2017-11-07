package table;

import exceptions.MismatchedColumns;
import exceptions.MissingHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class that represents a table and serves as a container
 * for rows and header.
 *
 * Note that much of the functionality and attributes of tables
 * is derived from headers and rows. Table without header or rows
 * does not make much sense. In fact, for table to exist it must
 * have at least a header.
 */
public class Table {

    private final Header header;
    private List<DataRow> rows;
    private final int width;

    public Table(Header header) {
        this.header = Optional.ofNullable(header).orElseThrow(() -> new MissingHeader("Table must have a header"));
        this.width = header.getWidth();
        this.rows = new ArrayList<>();
    }

    public void addRow(DataRow row) {
        requireSameSize(row);
        requireMatchingColumns(row);

        rows.add(row);
    }

    private void requireSameSize(DataRow row) {
        if (header.columnCount() != row.columnCount()) {
            throw new MismatchedColumns("Header (" + header.columnCount() +") " +
                    "and row (" + row.columnCount() + ") column count does not match");
        }
    }

    private void requireMatchingColumns(DataRow row) {
        List<String> rowColumns = row.getColumnNames();

        for (String column : rowColumns) {
            if (!header.containsColumn(column)) {
                throw new MismatchedColumns("Value is missing from the header: " + column);
            }
        }
    }

    public int columnCount() {
        return header.columnCount();
    }

    /**
     * Answers the question of whether the table has any rows.
     * Note that header is not considered to be a row, as headers
     * do not contain any data aside from column names.
     *
     * @return whether the table has rows
     */
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public Header getHeader() {
        return header;
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public int getWidth() {
        return width;
    }
}
