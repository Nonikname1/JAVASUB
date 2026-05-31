package db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static volatile String URL;
    private static volatile String USER;
    private static volatile String PASS;

    public static void setConfigPath(String path) {
        URL = null;
        load(path);
    }

    private static void ensureLoaded() {
        if (URL == null) {
            synchronized (ConnectionFactory.class) {
                if (URL == null) {
                    load("db.properties");
                }
            }
        }
    }

    private static void load(String path) {
        try (InputStream in = new FileInputStream(path)) {
            Properties props = new Properties();
            props.load(in);
            URL  = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки " + path, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        ensureLoaded();
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
