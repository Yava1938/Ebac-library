package com.ebac.biblioteca.dao.interfaces;

import com.ebac.biblioteca.dto.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookDAO {

    Book save(Book book) throws SQLException;
    Optional<Book> findById(int id) throws SQLException;
    List<Book> findAll() throws SQLException;
    boolean update(Book book) throws SQLException;
    boolean delete(int id) throws SQLException;
}