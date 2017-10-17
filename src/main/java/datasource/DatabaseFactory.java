package datasource;

import configuration.JdbcConfiguration;

public class DatabaseFactory {

    public static Database createDatabase(JdbcConfiguration configuration) {
        switch (configuration.name) {
            case "hsqldb":
                return new HsqlDatabase(configuration);
            default:
                return new HsqlDatabase(configuration);
        }
    }
}
