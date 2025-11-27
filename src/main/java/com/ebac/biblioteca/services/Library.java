package com.ebac.biblioteca.services;

import com.ebac.biblioteca.dto.Autor;
import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Library {

    Book addBook(Book book) throws SQLException;
    Optional<Book> getBookById(int id) throws SQLException;
    List<Book> searchBooks() throws SQLException;
    void updateBook(Book book) throws SQLException;
    void deleteBook(int id) throws SQLException;
    void lendBook(int id_ibro, int id_usuario) throws SQLException;
    void  returnBook(int id_libro, int id_usuario) throws SQLException;

    User addUser(User user) throws SQLException;
    Optional<User> getUserById(int id) throws SQLException;
    List<User> searchUsers() throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;

    Autor addAutor(Autor autor) throws SQLException;
    Optional<Autor> getAutorById(int id) throws SQLException;
    List<Autor> searchAutor() throws SQLException;
    void updateAutor(Autor autor) throws SQLException;
    void deleteAutor(int id) throws SQLException;

}
