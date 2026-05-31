package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    public static void init() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(
                "CREATE TABLE IF NOT EXISTS groups (" +
                "  id   INTEGER PRIMARY KEY," +
                "  name VARCHAR(100) NOT NULL" +
                ")"
            );
            st.execute(
                "CREATE TABLE IF NOT EXISTS students (" +
                "  id       INTEGER PRIMARY KEY," +
                "  name     VARCHAR(100) NOT NULL," +
                "  group_id INTEGER NOT NULL REFERENCES groups(id)," +
                "  task1    BOOLEAN NOT NULL DEFAULT FALSE," +
                "  task2    BOOLEAN NOT NULL DEFAULT FALSE," +
                "  task3    BOOLEAN NOT NULL DEFAULT FALSE" +
                ")"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось инициализировать схему БД", e);
        }
    }
}
