package table;

import exceptions.InvalidWidth;
import exceptions.MismatchedWidth;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeaderTest {

    @Test
    public void constructorSetsWidth() {
        Header header = new Header(80);

        assertThat(header.getWidth(), is(80));
    }

    @Test(expected = InvalidWidth.class)
    public void constructorRefusesInvalidWidth() {
        new Header(0);
    }

    @Test
    public void setsColumnsWithCorrectWidth() {
        Map<String, Integer> columns = Map.of("column1", 10, "column2", 67);
        int columnWidth = columns.values().stream().mapToInt(Number::intValue).sum();
        int totalBordersWidth = columns.size() + 1;

        Header header = new Header(80);
        header.setColumns(columns);

        assertThat(header.getWidth(), is(columnWidth + totalBordersWidth));
    }

    @Test(expected = MismatchedWidth.class)
    public void doesNotAcceptTooWideColumns() {
        Map<String, Integer> columns = Map.of("column1", 10, "column2", 1);

        Header header = new Header(10);
        header.setColumns(columns);
    }

    @Test(expected = MismatchedWidth.class)
    public void includesBorderWidthsInCalculations() {
        Map<String, Integer> columns = Map.of("column1", 10, "column2", 20);

        Header header = new Header(20);
        header.setColumns(columns);
    }
}