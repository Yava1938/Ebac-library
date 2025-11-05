package com.ebac.biblioteca.services;

import com.ebac.biblioteca.modelos.Book;
import com.ebac.biblioteca.modelos.User;

import java.util.List;
import java.util.Optional;

public interface Library {

    void addBook(Book book);
    Optional<Book> getBookByISBN(String isbn);
    List<Book> searchBookByTitle(String title);
    void addUser(User user);
    Optional<User> getUserByEmail(String email);
    void lendBook(String isbn, String email);
    void  returnBook(String isbn, String email);
}
