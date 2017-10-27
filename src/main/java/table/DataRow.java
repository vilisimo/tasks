package table;

import java.util.Map;

class DataRow {

    private final Map<String, String> columns;

    DataRow(Map<String, String> columns) {
        this.columns = columns;
    }

    public Map<String, String> getColumns() {
        return columns;
    }
}
