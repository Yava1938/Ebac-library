package com.ebac.biblioteca.services;

import com.ebac.biblioteca.dto.Author;
import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Library {

    Book addBook(Book book);
    Optional<Book> getBookById(Long id);
    List<Book> searchBooks();
    Book updateBook(Book book);
    void deleteBook(Long id);

    Book lendBook(Long bookId, Long userId);
    Book returnBook(Long bookId);

    User addUser(User user);
    Optional<User> getUserById(Long id);
    List<User> searchUsers();
    User updateUser(User user);
    void deleteUser(Long id);

    Author addAuthor(Author author);
    Optional<Author> getAuthorById(Long id);
    List<Author> searchAuthors();
    Author updateAuthor(Author author);
    void deleteAuthor(Long id);

}
