package com.ebac.biblioteca.dao.implementador;

import com.ebac.biblioteca.configuracion.MyConnection;
import com.ebac.biblioteca.dao.interfaces.UserDAO;
import com.ebac.biblioteca.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserImp implements UserDAO {
    private static final  String tabla = "usuarios";
    private final Connection conn;

    public UserImp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User save(User user) throws SQLException {
        String sql = "Insert into " + tabla + " (nombre_usuario, email_usuario, password_usuario, fecha_registro) " +
                "values (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getNombre_usuario());
        statement.setString(2, user.getEmail_usuario());
        statement.setString(3, user.getPassword_usuario());
        statement.setDate(4, user.getFecha_registro());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        if (keys.next()){
            user.setId_usuario(keys.getInt(1));
        }
        return user;
    }

    @Override
    public Boolean update(User user) throws SQLException {
        String sql = "Update " +  tabla + " set nombre_usuario = ?, " +
                " email_usuario = ?, password_usuario = ? " +
                " where id_usuario = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getNombre_usuario());
        statement.setString(2, user.getEmail_usuario());
        statement.setString(3, user.getPassword_usuario());
        statement.setInt(4, user.getId_usuario());

        return statement.executeUpdate() == 1;
    }

    @Override
    public Boolean delete(int id_user) throws SQLException {
        String sql = "Delete from " + tabla + " where id_usuario = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id_user);
        return statement.executeUpdate() == 1;
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        String sql = "Select * from " +tabla+ " where id_usuario = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        return rs.next()? Optional.of(map(rs)):  Optional.empty();
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "Select * from " +tabla;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<User> users = new ArrayList<>();
        while (rs.next()){
            users.add(map(rs));
        }

        return users;
    }

    private User map(ResultSet rs) throws SQLException{
        User user = new User();
        user.setId_usuario(rs.getInt("id_usuario"));
        user.setNombre_usuario(rs.getString("nombre_usuario"));
        user.setEmail_usuario(rs.getString("email_usuario"));
        user.setPassword_usuario(rs.getString("password_usuario"));
        user.setFecha_registro(rs.getDate("fecha_registro"));
        return  user;
    }
}
