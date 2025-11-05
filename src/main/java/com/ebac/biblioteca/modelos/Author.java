package com.ebac.biblioteca.modelos;

import java.util.ArrayList;
import java.util.List;

public class Author {

    private String firstName;
    private String lastName;
    private String biography;
    private List<Book> books = new ArrayList<>();

    public Author(String firstName, String lastName, String biography) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.biography = biography;
    }

    public String getFullName(){ return firstName + " " + lastName; }

    public List<Book> getBooks(){ return books;}
    public void addBook(Book book){
        if (!books.contains(book)){
            books.add(book);
        }else{
            System.out.println("Libro ya registrado");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
