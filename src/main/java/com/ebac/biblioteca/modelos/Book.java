package com.ebac.biblioteca.modelos;

import java.util.Objects;

public class Book {

    private String Title;
    private int Year;
    private Author Author;
    private String ISBN;
    private boolean availabe =  true;

    public Book(String title, int year, Author author, String ISBN) {
        Title = title;
        Year = year;
        Author = author;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public Author getAuthor() {
        return Author;
    }

    public void setAuthor(Author author) {
        Author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isAvailabe() {
        return availabe;
    }

    public void setAvailabe(boolean availabe) {
        this.availabe = availabe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book b = (Book) o;
        return Objects.equals(ISBN, b.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public String toString() {
        return Title + " (" + Year + ") - " + Author.getFullName();
    }
}
