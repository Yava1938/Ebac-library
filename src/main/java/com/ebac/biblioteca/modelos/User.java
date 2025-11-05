package com.ebac.biblioteca.modelos;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String password;
    private List<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public void addBorrowedBook(Book book){
        borrowedBooks.add(book);
    }
    public void removeBorrowedBook(Book book){
        borrowedBooks.remove(book);
    }
}
