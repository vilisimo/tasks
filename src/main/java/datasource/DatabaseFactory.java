package datasource;

import configuration.JdbcConfiguration;

public final class DatabaseFactory {

    private DatabaseFactory() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static Database createDatabase(JdbcConfiguration configuration) {
        switch (configuration.name) {
            case "hsqldb":
                return new HsqlDatabase(configuration);
            default:
                return new HsqlDatabase(configuration);
        }
    }
}
