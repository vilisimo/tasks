import java.sql.*;

public class Main {
    public static void main(String args[]) {
        try (Connection connection = connect()) {
            createTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() {
        Connection connection = null;

        try {
            // /tododb;ifexists=true would allow to connect only to an existing db
            connection = DriverManager.getConnection("jdbc:hsqldb:file:~/temporary/tododb", "SA", "");

            if (connection != null) {
                System.out.println("Successfully established connection!");
            } else {
                System.out.println("Connection was not established...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private static int createTable(Connection connection) {
        int result = 0;
        try (Statement statement = connection.createStatement()) {
            result = statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS tasks(id INT NOT NULL, description VARCHAR(100) NOT NULL, PRIMARY KEY(id))");
            System.out.println("Successfully created a table!");

            try (ResultSet rs = statement.executeQuery("SELECT id FROM tasks")) {
                System.out.println(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
