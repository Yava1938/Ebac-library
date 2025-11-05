package com.ebac.biblioteca.services;

import com.ebac.biblioteca.exceptions.BookNotAvailableException;
import com.ebac.biblioteca.exceptions.DuplicateUserException;
import com.ebac.biblioteca.exceptions.UserNotFoundException;
import com.ebac.biblioteca.modelos.Book;
import com.ebac.biblioteca.modelos.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LibraryImpl implements Library{
    private final Map<String, Book> booksByIsbn = new ConcurrentHashMap<>();
    private final Map<String, User> userByEmail = new ConcurrentHashMap<>();

    @Override
    public void addBook(Book book) {
        booksByIsbn.putIfAbsent(book.getISBN(), book);
    }

    @Override
    public Optional<Book> getBookByISBN(String isbn) {
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(User user) {
        if (userByEmail.putIfAbsent(user.getEmail(), user) != null){
            throw new DuplicateUserException("Usuario ya existe: " + user.getEmail());
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userByEmail.get(email));
    }

    @Override
    public void lendBook(String isbn, String email) {
        Book book = booksByIsbn.get(isbn);
        User user =  userByEmail.get(email);
        if (user == null) throw new UserNotFoundException(email);
        if (book == null) throw new RuntimeException("Libro no encontrado: " + isbn);
        if (!book.isAvailabe()) throw new BookNotAvailableException(isbn + " No disponible");
        book.setAvailabe(false);
        user.addBorrowedBook(book);
    }

    @Override
    public void returnBook(String isbn, String email) {
        Book book = booksByIsbn.get(isbn);
        User user =  userByEmail.get(email);
        if (user == null) throw new UserNotFoundException(email);
        if (book == null) throw new RuntimeException("Libro no encontrado: " + isbn);
        if (user.getBorrowedBooks().contains(book)){
            user.removeBorrowedBook(book);
            book.setAvailabe(true);
        }else{
            throw new RuntimeException("Usuario no cuenta con: " + isbn + " como prestado");
        }
    }

    public List<Book> searchBooks(String title, String authorName, Integer yearMin, Integer yearMax){
        return  booksByIsbn.values().stream()
                .filter(book -> title == null || book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter( book -> authorName == null || book.getAuthor().getFullName().toLowerCase().contains(authorName.toLowerCase()))
                .filter(book -> yearMin == null || book.getYear() >= yearMin)
                .filter(book -> yearMax == null || book.getYear() <= yearMax)
                .sorted(Comparator.comparing(Book::getYear))
                .collect(Collectors.toList());
    }

    public Map<String, List<Book>> searchBooksByAuthor(){
        return booksByIsbn.values().stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getFullName()));
    }

    public List<String> getBorrowedBooksByUser(String email){
        return Optional.ofNullable(userByEmail.get(email))
                .map(User::getBorrowedBooks)
                .orElse(List.of())
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(userByEmail.values());
    }



}
