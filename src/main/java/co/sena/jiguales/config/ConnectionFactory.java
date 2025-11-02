package co.sena.jiguales.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Obtiene conexiones JDBC leyendo src/main/resources/db.properties
 */
public final class ConnectionFactory {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("No se encontró db.properties en resources");
            PROPS.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración de BD", e);
        }
    }

    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("url"),
                PROPS.getProperty("user"),
                PROPS.getProperty("password")
        );
    }
}
