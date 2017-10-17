package configuration;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static configuration.ConfigurationLoader.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConfigurationLoaderTest {

    private static final String TEST_FILE_LOCATION = "test.properties";

    @Test
    public void loadsExistingFile() {
        JdbcConfiguration configuration = ConfigurationLoader.loadJdbcConfig(TEST_FILE_LOCATION);

        assertThat(configuration.name, is(notNullValue()));
        assertThat(configuration.url, is(notNullValue()));
        assertThat(configuration.password, is(notNullValue()));
        assertThat(configuration.username, is(notNullValue()));
        assertThat(configuration.driver, is(notNullValue()));
    }

    @Test(expected = NullPointerException.class)
    public void doesNotAcceptNullPath() {
        loadJdbcConfig(null);
    }

    @Test(expected = RuntimeException.class)
    public void throwsExceptionWithInvalidPath() throws FileNotFoundException {
        getInputStream("invalid");
    }

    @Test
    public void validInputStreamReturnsPropertiesFile() {
        byte[] propertiesBytes = "jdbc.driver=test\njdbc.url=testUrl".getBytes();
        InputStream input = new ByteArrayInputStream(propertiesBytes);

        Properties properties = loadProperties(input);

        assertThat(properties.getProperty("jdbc.driver"), is("test"));
        assertThat(properties.getProperty("jdbc.url"), is("testUrl"));
    }

    @Test(expected = RuntimeException.class)
    public void throwsExceptionWithInvalidStream() {
        loadProperties(null);
    }

    @Test
    public void configurationFileIsReturned() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.name", "testName");
        properties.setProperty("jdbc.driver", "testDriver");
        properties.setProperty("jdbc.url", "testUrl");
        properties.setProperty("jdbc.username", "testUsername");
        properties.setProperty("jdbc.password", "");

        JdbcConfiguration configuration = ConfigurationLoader.createJdbcConfig(properties);

        assertThat(configuration.name, is("testName"));
        assertThat(configuration.driver, is("testDriver"));
        assertThat(configuration.url, is("testUrl"));
        assertThat(configuration.username, is("testUsername"));
        assertThat(configuration.password, is(""));
    }

    @Test(expected = NullPointerException.class)
    public void configurationFileRequiresName() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "testDriver");
        properties.setProperty("jdbc.url", "testUrl");
        properties.setProperty("jdbc.username", "testUsername");
        properties.setProperty("jdbc.password", "");

        ConfigurationLoader.createJdbcConfig(properties);
    }

    @Test(expected = NullPointerException.class)
    public void configurationFileRequiresDriver() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.url", "testUrl");
        properties.setProperty("jdbc.username", "testUsername");
        properties.setProperty("jdbc.password", "");

        ConfigurationLoader.createJdbcConfig(properties);
    }

    @Test(expected = NullPointerException.class)
    public void configurationFileRequiresUrl() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "testDriver");
        properties.setProperty("jdbc.username", "testUsername");
        properties.setProperty("jdbc.password", "");

        ConfigurationLoader.createJdbcConfig(properties);
    }

    @Test(expected = NullPointerException.class)
    public void configurationFileRequiresUsername() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "testDriver");
        properties.setProperty("jdbc.url", "testUrl");
        properties.setProperty("jdbc.password", "");

        ConfigurationLoader.createJdbcConfig(properties);
    }

    @Test(expected = NullPointerException.class)
    public void configurationFileRequiresPassword() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", "testDriver");
        properties.setProperty("jdbc.url", "testUrl");
        properties.setProperty("jdbc.username", "testUsername");

        ConfigurationLoader.createJdbcConfig(properties);
    }
}