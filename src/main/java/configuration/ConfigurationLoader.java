package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * The class is responsible for loading external properties
 * file(s).
 */
public class ConfigurationLoader {

    private final static Logger logger = LogManager.getLogger();

    public static JdbcConfiguration loadJdbcConfig(String path) {
        requireNonNull(path, "Path was not provided");

        InputStream input = getInputStream(path);
        logger.trace("Loaded input stream from {}", path);
        Properties properties = loadProperties(input);
        logger.info("Successfully loaded properties from {}", path);

        return createJdbcConfig(properties);
    }

    /**
     * Converts a file at a given location to InputStream
     * @param path file's location
     * @return {@code InputStream} of the file
     */
    static InputStream getInputStream(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream(path);

        if (stream == null) {
            throw new RuntimeException("Configuration file could not be found at the specified location (" + path + ")");
        }

        return stream;
    }

    static Properties loadProperties(InputStream input) {
        Properties properties = new Properties();
        try {
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new RuntimeException("Properties file could not be loaded", exception);
        }
    }

    static JdbcConfiguration createJdbcConfig(Properties properties) {
        JdbcConfiguration configuration = new JdbcConfiguration();

        configuration.name = ofNullable(properties.getProperty("jdbc.name"))
                .orElseThrow(() -> new NullPointerException("\"jdbc.name\" property is missing"));
        configuration.driver = ofNullable(properties.getProperty("jdbc.driver"))
                .orElseThrow(() -> new NullPointerException("\"jdbc.driver\" property is missing"));
        configuration.url = ofNullable(properties.getProperty("jdbc.url"))
                .orElseThrow(() -> new NullPointerException("\"jdbc.url\" property is missing"));
        configuration.username = ofNullable(properties.getProperty("jdbc.username"))
                .orElseThrow(() -> new NullPointerException("\"jdbc.username\" property is missing"));
        configuration.password = ofNullable(properties.getProperty("jdbc.password"))
                .orElseThrow(() -> new NullPointerException("\"jdbc.password\" property is missing"));

        return configuration;
    }
}
