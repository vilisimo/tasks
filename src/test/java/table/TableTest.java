package table;

import exceptions.MissingHeader;
import exceptions.MissingColumn;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TableTest {

    private Header header;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        header = new Header(80);
    }

    @Test
    public void constructorSetsWidth() {
        Table table = new Table(header);

        assertThat(table.getWidth(), is(header.getWidth()));
    }

    @Test(expected = MissingHeader.class)
    public void constructorDoesNotAcceptNullHeader() {
        new Table(null);
    }

    @Test
    public void getsHeaderColumns() {
        header.setColumns(Map.of("key", 78));
        Table table = new Table(header);

        Map<String, Integer> columns = table.getHeaderColumns();

        assertThat(columns, is(Map.of("key", 78)));
    }

    @Test
    public void getsListOfRows() {
        header.setColumns(Map.of("key1", 78));
        Table table = new Table(header);

        table.addRow(Map.of("key1", "value1", "key2", "value1"));
        table.addRow(Map.of("key1", "value2", "key2", "value2"));

        assertThat(table.getRows().size(), is(2));
    }

    @Test(expected = MissingHeader.class)
    public void refusesRowsWhenHeaderNotSet() {
        Table table = new Table(header);

        table.addRow(Map.of("key", "value"));
    }

    @Test(expected = MissingColumn.class)
    public void refusesRowsThatDoNotHaveHeadersColumns() {
        header.setColumns(Map.of("key", 78));
        Table table = new Table(header);

        table.addRow(Map.of("invalidKey", "value"));
    }

    @Test
    public void refusesRowsThatHaveFewerColumnsThanHeader() {
        expectedException.expect(MissingColumn.class);
        expectedException.expectMessage("The row has less columns than the header");
        header.setColumns(Map.of("key1", 70, "key2", 7));
        Table table = new Table(header);

        table.addRow(Map.of("key1", "value1"));
    }
}