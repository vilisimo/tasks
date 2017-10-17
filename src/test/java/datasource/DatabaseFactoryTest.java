package datasource;

import configuration.JdbcConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DatabaseFactoryTest {

    private JdbcConfiguration configuration;

    @Before
    public void setup() {
        configuration = new JdbcConfiguration();
        configuration.name = "hsqldb";
        configuration.username = "sa";
        configuration.password = "";
        configuration.url = "jdbc:hsqldb:mem:test";
        configuration.driver = "irrelevant";
    }

    @Test
    public void returnsHsql() {
        Database database = DatabaseFactory.createDatabase(configuration);

        assertThat(database.getClass().getSimpleName(), is("HsqlDatabase"));
    }

    @Test
    public void returnsDefault() {
        configuration.name = "irrelevant";
        Database database = DatabaseFactory.createDatabase(configuration);

        assertThat(database.getClass().getSimpleName(), is("HsqlDatabase"));
    }
}