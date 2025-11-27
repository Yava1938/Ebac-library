package com.ebac.biblioteca.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER ="root";
    private static final String PASSWORD = "root";

    private static Connection conn;

    private MyConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()){
            synchronized (MyConnection.class) {
                if (conn == null || conn.isClosed()) {
                    conn = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Conexión a MySQL establecida correctamente.");
                }
            }
        }
        return conn;
    }

    public static void closeConnection(){
        try {
            if (conn != null && !conn.isClosed()){
                conn.close();
                System.out.println("Conexión a MySQL cerrada correctamente");
            }
        } catch (SQLException err) {
            System.out.println("Error al cerrar la conexión: " + err.getMessage());
        }
    }
}
