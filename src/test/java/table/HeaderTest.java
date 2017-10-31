package table;

import exceptions.EmptyCollection;
import exceptions.MissingColumn;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class HeaderTest {

    @Test
    public void constructorSetsWidth() {
        Map<String, Integer> columns = Map.of("key1", 20, "key2", 50, "key3", 6);
        Header header = new Header(columns);

        assertThat(header.getWidth(), is(76));
    }

    @Test(expected = EmptyCollection.class)
    public void constructorRefusesEmptyHeader() {
        new Header(Collections.emptyMap());
    }

    @Test(expected = NullPointerException.class)
    public void constructorRefusesNullMap() {
        new Header(null);
    }

    @Test
    public void returnsListOfKeys() {
        Header header = new Header(Map.of("key1", 1, "key2", 2, "key3", 3));

        List<String> headerColumns = header.getColumns();

        assertThat(headerColumns, containsInAnyOrder("key1", "key2", "key3"));
    }

    @Test
    public void orderedMapReturnsListWithSameOrderOfKeys() {
        Map<String, Integer> orderedMap = new LinkedHashMap<>();
        orderedMap.put("key1", 1);
        orderedMap.put("key2", 2);
        orderedMap.put("key3", 3);
        Header header = new Header(orderedMap);

        List<String> headerColumns = header.getColumns();

        assertThat(headerColumns, is(List.of("key1", "key2", "key3")));
    }

    @Test
    public void returnsColumnWidth() {
        Header header = new Header(Map.of("key1", 1, "key2", 2));

        assertThat(header.getColumnWidth("key1"), is(1));
        assertThat(header.getColumnWidth("key2"), is(2));
    }

    @Test(expected = MissingColumn.class)
    public void recognizesMissingColumnWhenAskedForWidth() {
        Header header = new Header(Map.of("key1", 1, "key2", 2));

        header.getColumnWidth("key3");
    }

    @Test
    public void recognizesMissingColumn() {
        Header header = new Header(Map.of("key1", 1));

        assertThat(header.containsColumn("key1"), is(true));
        assertThat(header.containsColumn("key2"), is(false));
    }

    @Test
    public void containsAllRecognizesAllValues() {
        Header header = new Header(Map.of("key1", 1, "key2", 2));

        assertThat(header.containsColumns(List.of("key1", "key2")), is(true));
    }

    @Test
    public void containsAllRecognizesMissingValues() {
        Header header = new Header(Map.of("key1", 1, "key2", 2));

        assertThat(header.containsColumns(List.of("key1", "key2", "key3")), is(false));
    }

    @Test(expected = NullPointerException.class)
    public void dealsWithNullValues() {
        Header header = new Header(Map.of("key1", 1, "key2", 2));

        assertThat(header.containsColumns(null), is(false));
    }

    @Test
    public void returnsColumnCount() {
        Header header = new Header(Map.of("key1", 1, "key2", 2, "key3", 3));

        assertThat(header.columnCount(), is(3));
    }

    @Test
    public void doesNotAllowToModifyMapAfterAddingItToHeader() {
        Map<String, Integer> columns = new HashMap<>();
        columns.put("key1", 1);

        Header header = new Header(columns);
        columns.put("key2", 2);

        assertThat(header.columnCount(), is(1));
    }

    @Test
    public void retrievesCorrectValuesBasedOnColumnPosition() {
        Map<String, Integer> orderedMap = new LinkedHashMap<>();
        orderedMap.put("key1", 1);
        orderedMap.put("key2", 2);
        Header header = new Header(orderedMap);

        assertThat(header.getColumnWidth(0), is(1));
        assertThat(header.getColumnWidth(1), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotAcceptNegativeColumnPosition() {
        Header header = new Header(Map.of("key1", 1, "key2", 2));

        header.getColumnWidth(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesNotAcceptLargerThanSizePositions() {
        Header header = new Header(Map.of("key1", 1));

        header.getColumnWidth(1);
    }
}