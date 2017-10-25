package datasource;

import java.sql.Timestamp;

final class Statements {

    private Statements() {
        throw new AssertionError("The class should not be instantiated");
    }

    static String select() {
        return "SELECT * FROM TASKS";
    }

    static String insert() {
        return "INSERT INTO TASKS(description, deadline) VALUES (?, ?)";
    }

    static String delete() {
        return "DELETE FROM TASKS WHERE TASKS.ID=?";
    }

    static String truncate() {
        return "TRUNCATE TABLE TASKS AND COMMIT";
    }

    static String filter(Timestamp deadline) {
        return deadline == null ? "SELECT * FROM TASKS WHERE deadline IS NULL" : "SELECT * FROM TASKS WHERE deadline = ?";
    }
}
