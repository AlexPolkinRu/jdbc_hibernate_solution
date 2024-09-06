package jm.task.core.jdbc.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    // реализуйте настройку соединения с БД
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE = "src/main/resources/application.properties";
    private static final String PROPERTY_NAME_URL = "url";
    private static final String PROPERTY_NAME_USER = "username";
    private static final String PROPERTY_NAME_PASS = "password";

    public static Connection getConnection() {

        try {
            PROPERTIES.load(new BufferedInputStream(new FileInputStream(PROPERTIES_FILE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
            Connection connection = DriverManager.getConnection(
                    PROPERTIES.getProperty(PROPERTY_NAME_URL),
                    PROPERTIES.getProperty(PROPERTY_NAME_USER),
                    PROPERTIES.getProperty(PROPERTY_NAME_PASS)
            )
        ) {
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
