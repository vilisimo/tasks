package datasource;

import configuration.JdbcConfiguration;

public class DatabaseFactory {

    public static Database createDatabase(String name, JdbcConfiguration configuration) {
        switch (name) {
            case "hsql":
                return new HsqlDatabase(configuration);
            default:
                return new HsqlDatabase(configuration);
        }
    }
}
