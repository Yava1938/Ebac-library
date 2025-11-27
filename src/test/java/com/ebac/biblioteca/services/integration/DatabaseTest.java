package com.ebac.biblioteca.services.integration;

import com.ebac.biblioteca.configuracion.MyConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTest {

    protected Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        conn = MyConnection.getConnection();
        cleanDatabase();
    }

    @AfterEach
    void limpiarTodo() throws SQLException{
        cleanDatabase();
    }

    protected void cleanDatabase() throws SQLException{
        Statement st = conn.createStatement();
        st.executeUpdate("Set foreign_key_checks = 0");
        st.executeUpdate("truncate table libros");
        st.executeUpdate("truncate table usuarios");
        st.executeUpdate("truncate table autores");
        st.executeUpdate("Set foreign_key_checks = 1");
        st.close();
    }
}
