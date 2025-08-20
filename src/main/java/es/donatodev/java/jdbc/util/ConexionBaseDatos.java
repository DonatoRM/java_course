package es.donatodev.java.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=Europe/Madrid";
    private static final String USERNAME ="root";
    private static final String PASSWORD = "donato";
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if(connection==null) {
            connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }
}
