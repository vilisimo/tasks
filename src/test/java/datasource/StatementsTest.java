package datasource;

import org.junit.Test;

import java.sql.Timestamp;

import static datasource.Statements.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatementsTest {

    @Test
    public void selectReturnsCorrectStatement() {
        String expected = "SELECT * FROM TASKS";

        assertThat(select(), is(expected));
    }

    @Test
    public void insertReturnsCorrectStatement() {
        String expected = "INSERT INTO TASKS(description, deadline) VALUES (?, ?)";

        assertThat(insert(), is(expected));
    }

    @Test
    public void deleteReturnsCorrectStatement() {
        String expected = "DELETE FROM TASKS WHERE TASKS.ID=?";

        assertThat(delete(), is(expected));
    }

    @Test
    public void truncateReturnsCorrectStatement() {
        String expected = "TRUNCATE TABLE TASKS AND COMMIT";

        assertThat(truncate(), is(expected));
    }

    @Test
    public void filterDeadlineReturnsStatementWithIsNullWhenNullGiven() {
        String expected = "SELECT * FROM TASKS WHERE deadline IS NULL";

        assertThat(filter(null), is(expected));
    }

    @Test
    public void filterDeadlineReturnsStatementWithEqualsWhenValidValueGiven() {
        String expected = "SELECT * FROM TASKS WHERE deadline = ?";

        assertThat(filter(new Timestamp(1)), is(expected));
    }

}