package com.ebac.biblioteca.dao.implementador;

import com.ebac.biblioteca.configuracion.MyConnection;
import com.ebac.biblioteca.dao.interfaces.BookDAO;
import com.ebac.biblioteca.dto.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookImp implements BookDAO {
    private static final String tabla = "libros";
    private final Connection conn;

    public BookImp(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Book save(Book book) throws SQLException {
        System.out.println("Registrando libro: " + book.getNombre_libro());
        String sql = "Insert into " + tabla + " (nombre_libro, id_autor, anio_publicacion, disponible, id_usuario)" +
                " values(?, ?, ?, ?, NULL)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getNombre_libro());
        statement.setInt(2,book.getId_autor());
        statement.setInt(3, book.getAnio());
        statement.setBoolean(4, book.isDisponible());
        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();
        if (keys.next()) {
            book.setId_libro(keys.getInt(1));
        }
        return book;
    }

    @Override
    public Optional<Book> findById(int id) throws SQLException {
        String sql = "Select * from " + tabla + " where id_libro = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();

        return rs.next() ? Optional.of(map(rs)) : Optional.empty();
    }

    @Override
    public List<Book> findAll() throws SQLException {
        String sql  = "Select * from " +  tabla;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        List<Book> libros = new ArrayList<>();
        while (rs.next()){
            libros.add(map(rs));
        }
        return libros;
    }

    @Override
    public boolean update(Book book) throws SQLException {
        System.out.println("Actualizando libro: " + book.getNombre_libro());
        String sql = "Update " + tabla + " set nombre_libro = ?, id_autor = ?, anio_publicacion = ?, " +
                "disponible = ?, id_usuario = ? " +
                "where id_libro = ?";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, book.getNombre_libro());
        statement.setInt(2, book.getId_autor());
        statement.setInt(3, book.getAnio());
        statement.setBoolean(4, book.isDisponible());
        if (book.getId_usuario() == null){
            statement.setNull(5, Types.INTEGER);
        }else{
            statement.setInt(5, book.getId_usuario());
        }
        statement.setInt(6, book.getId_libro());

        return statement.executeUpdate() == 1;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        System.out.println("Eliminando libro: " + id);
        String sql =  "Delete from "+ tabla +  " where id_libro = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        return statement.executeUpdate() == 1;
    }

    private Book map(ResultSet rs) throws SQLException{
        Book book = new Book();
        book.setId_libro(rs.getInt("id_libro"));
        book.setNombre_libro(rs.getString("nombre_libro"));
        book.setId_autor(rs.getInt("id_autor"));
        book.setAnio(rs.getInt("anio_publicacion"));
        book.setDisponible(rs.getBoolean("disponible"));

        int id_usuario = rs.getInt("id_usuario");
        book.setId_usuario(rs.wasNull() ? null : id_usuario);

        return book;
    }
}
