package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static Properties properties = new Properties();

    static {
        try {
            // Leemos el archivo de configuración
            // IMPORTANTE: Esta ruta es relativa a donde ejecutas el comando java
            properties.load(new FileInputStream("src/com/procesadora/resources/config.properties"));
        } catch (IOException ex) {
            System.err.println("Error: No se encuentra config.properties. Revisa la ruta.");
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password")
        );
    }
}