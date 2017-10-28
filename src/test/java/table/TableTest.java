package table;

import exceptions.MismatchedColumns;
import exceptions.MissingHeader;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TableTest {

//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//

    private final Header header = new Header(Map.of("key1", 1, "key2", 2));

    @Test
    public void constructorSetsWidth() {
        Table table = new Table(header);
        int bordersWidth = header.columnCount() + 1;

        assertThat(table.getWidth(), is(header.getWidth() + bordersWidth));
    }

    @Test(expected = MissingHeader.class)
    public void constructorDoesNotAcceptNullHeader() {
        new Table(null);
    }

    @Test
    public void tableRecognizesWhenThereAreNoRows() {
        Table table = new Table(new Header(Map.of("key", 1)));

        assertThat(table.isEmpty(), is(true));
    }

    @Test(expected = MismatchedColumns.class)
    public void tableRecognizesMismatchedHeaderAndDataRowSizes() {
        Table table = new Table(new Header(Map.of("key1", 1, "key2", 2)));

        table.addRow(new DataRow(Map.of("key1", "val1")));
    }

    @Test(expected = MismatchedColumns.class)
    public void tableRecognizesRowsThatHaveValuesNotInTheHeader() {
        Table table = new Table(new Header(Map.of("key1", 1, "key2", 2)));

        table.addRow(new DataRow(Map.of("key1", "val1", "key3", "val3")));
    }

    @Test
    public void returnsAllAddedRows() {
        Table table = new Table(new Header(Map.of("key1", 1, "key2", 2)));
        table.addRow(new DataRow(Map.of("key1", "val1", "key2", "val2")));
        table.addRow(new DataRow(Map.of("key1", "val3", "key2", "val4")));
        table.addRow(new DataRow(Map.of("key1", "val5", "key2", "val6")));

        assertThat(table.getRows().size(), is(3));
    }
}