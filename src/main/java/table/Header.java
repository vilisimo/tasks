package table;

import exceptions.InvalidWidth;
import exceptions.MismatchedWidth;

import java.util.Collections;
import java.util.Map;

public class Header {

    private final int width;
    private Map<String, Integer> columns = Collections.emptyMap();

    public Header(int width) {
        if (width < 1) {
            throw new InvalidWidth("Width must be greater than 0");
        }

        this.width = width;
    }

    public void setColumns(Map<String, Integer> columns) {
        verifyWidth(columns);
        this.columns = Collections.unmodifiableMap(columns);
    }

    private void verifyWidth(Map<String, Integer> columns) {
        int columnsWidth = columns.values().stream().mapToInt(Number::intValue).sum();
        int totalBorderWidth = columns.size() + 1;
        int availableWidth = width - totalBorderWidth;

        if (columnsWidth > width) {
            throw new MismatchedWidth("Width of the columns exceeds width of the header");
        } else if (columnsWidth < width - availableWidth) {
            throw new MismatchedWidth("Header width mismatch. Available width: " + availableWidth
                    + ". Used width: " + columnsWidth);
        }
    }

    public Map<String, Integer> getColumns() {
        return columns;
    }

    public int getWidth() {
        return width;
    }
}
