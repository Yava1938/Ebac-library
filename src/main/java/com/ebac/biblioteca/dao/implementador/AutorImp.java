package com.ebac.biblioteca.dao.implementador;

import com.ebac.biblioteca.configuracion.MyConnection;
import com.ebac.biblioteca.dao.interfaces.AutorDAO;
import com.ebac.biblioteca.dto.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorImp implements AutorDAO {
    private static final String tabla = "autores";
    private final Connection conn;

    public AutorImp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Autor save(Autor autor) throws SQLException {
        String sql = "Insert into " + tabla + " (nombre_autor, nacionalidad) values " +
                " (?, ? )";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,autor.getNombre_autor());
        statement.setString(2, autor.getNacionalidad());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        if (keys.next()) {
            autor.setId_autor(keys.getInt(1));
        }
        return autor;
    }

    @Override
    public Autor update(Autor autor) throws SQLException {
        String sql = "Update " + tabla + " set nombre_autor = ?, nacionalidad = ? " +
                " where id_autor = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, autor.getNombre_autor());
        statement.setString(2, autor.getNacionalidad());
        statement.setInt(3, autor.getId_autor());
        if (statement.executeUpdate() == 1){
            System.out.println("Se actualizo correctamente al autor: " + autor.getId_autor());
        }
        return autor;
    }

    @Override
    public Boolean deleteById(int id_autor) throws SQLException {
        String sql = "delete from " + tabla + " where id_autor = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id_autor);
        return statement.executeUpdate() == 1;
    }

    @Override
    public Optional<Autor> findById(int id_autor) throws SQLException {
        String sql = "Select * from " + tabla +  " where id_autor = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id_autor);
        ResultSet rs = statement.executeQuery();

        return rs.next() ? Optional.of(map(rs)) : Optional.empty();
    }

    @Override
    public List<Autor> findAll() throws SQLException {
        String sql = "Select * from " +  tabla;
        PreparedStatement statement = conn.prepareStatement(sql);
        List<Autor> autores = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            autores.add(map(rs));
        }
        return autores;
    }

    private Autor map(ResultSet rs) throws SQLException {
        Autor autor = new Autor();
        autor.setId_autor(rs.getInt("id_autor"));
        autor.setNombre_autor(rs.getString("nombre_autor"));
        autor.setNacionalidad(rs.getString("nacionalidad"));
        return autor;
    }
}
