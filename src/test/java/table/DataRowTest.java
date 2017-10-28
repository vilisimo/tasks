package table;

import exceptions.EmptyCollection;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class DataRowTest {

    @Test(expected = EmptyCollection.class)
    public void constructorRefusesEmptyMap() {
        new DataRow(Collections.emptyMap());
    }

    @Test
    public void returnsListOfColumns() {
        DataRow row = new DataRow(Map.of("key1", "val", "key2", "val2", "key3", "val3"));

        assertThat(row.getColumnNames(), containsInAnyOrder("key1", "key2", "key3"));
    }

    @Test
    public void returnsListInOrderItWasAddedIfOrderedMapIsUsed() {
        Map<String, String> orderedMap = new LinkedHashMap<>();
        orderedMap.put("key1", "val1");
        orderedMap.put("key2", "val2");
        DataRow row = new DataRow(orderedMap);

        assertThat(row.getColumnNames(), is(List.of("key1", "key2")));
    }

    @Test
    public void returnsColumnCount() {
        Map<String, String> rowData = Map.of("key1", "val1", "key2", "val2");
        DataRow row = new DataRow(rowData);

        assertThat(row.columnCount(), is(rowData.size()));
    }

    @Test
    public void returnsColumnValue() {
        Map<String, String> rowData = Map.of("key1", "val1", "key2", "val2");
        DataRow row = new DataRow(rowData);

        assertThat(row.getColumnValue("key1"), is(rowData.get("key1")));
    }

    @Test
    public void doesNotAllowToModifyMapAfterAddingItToRow() {
        Map<String, String> columns = new HashMap<>();
        columns.put("key1", "val1");

        DataRow header = new DataRow(columns);
        columns.put("key2", "val2");

        assertThat(header.columnCount(), is(1));
    }
}