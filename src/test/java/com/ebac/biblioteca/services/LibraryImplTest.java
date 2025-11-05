package com.ebac.biblioteca.services;

import com.ebac.biblioteca.modelos.Author;
import com.ebac.biblioteca.modelos.Book;
import com.ebac.biblioteca.modelos.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryImplTest {

    private LibraryImpl libreria;
    private Author autor1;
    private Author autor2;
    private Author autor3;

    private Book book1;
    private Book book2;
    private Book book3;

    private User user;



    @BeforeEach
    void setUp(){
        libreria = new LibraryImpl();

        autor1 = new Author("Carlos Cuauhtémoc", "Sanchez", "Autor Mexicano");
        autor2 = new Author("Robert", "Greene", "Autor Estadounidense");
        autor3 = new Author("Sun", "Tzu", "Autor Chino");

        book1 = new Book("Los ojos de mi princesa", 1996,autor1,"9786077627463");
        book2 = new Book("El arte de la seduccion", 2001,autor2,"9786074006216");
        book3 = new Book("El arte de la guerra", 400,autor3,"9780670031566");

        autor1.addBook(book1);
        autor2.addBook(book2);
        autor3.addBook(book3);

        libreria.addBook(book1);
        libreria.addBook(book2);
        libreria.addBook(book3);

        user = new User("Yael", "yavac@ebac-estudiantes.org.mx","Mexico123");
        libreria.addUser(user);
    }


    @Test
    void addBook() {
        libreria.addBook(this.book1);

        Optional<Book> confirm = libreria.getBookByISBN(this.book1.getISBN());
        assertTrue(confirm.isPresent());
        assertEquals("Los ojos de mi princesa", confirm.get().getTitle());

    }

    @Test
    void getBookByISBN() {
        Optional<Book> result = libreria.getBookByISBN("9786074006216");
        assertTrue(result.isPresent());
        assertEquals("El arte de la seduccion", result.get().getTitle());
    }

    @Test
    void searchBookByTitle() {
        List<Book> results = libreria.searchBookByTitle("guerra");
        assertEquals(1, results.size());
        assertEquals("El arte de la guerra", results.get(0).getTitle());
    }

    @Test
    void addUser() {
        User newUser = new User("Mariely", "Mariela.143@ebac-estudientes.org.com", "Mexico123");
        libreria.addUser(newUser);

        Optional<User> retrieved = libreria.getUserByEmail("Mariela.143@ebac-estudientes.org.com");
        assertTrue(retrieved.isPresent());
        assertEquals("Mariely", retrieved.get().getName());
    }

    @Test
    void getUserByEmail() {
        Optional<User> result = libreria.getUserByEmail("yavac@ebac-estudiantes.org.mx");
        assertTrue(result.isPresent());
        assertEquals("Yael", result.get().getName());
    }

    @Test
    void lendBook() {
        libreria.lendBook("9786077627463", "yavac@ebac-estudiantes.org.mx");

        Book lentBook = libreria.getBookByISBN("9786077627463").get();
        assertFalse(lentBook.isAvailabe());
        assertTrue(user.getBorrowedBooks().contains(lentBook));
    }

    @Test
    void returnBook() {
        libreria.lendBook("9786074006216", "yavac@ebac-estudiantes.org.mx");
        libreria.returnBook("9786074006216", "yavac@ebac-estudiantes.org.mx");

        Book retorned = libreria.getBookByISBN("9786074006216").get();
        assertTrue(retorned.isAvailabe());
        assertFalse(user.getBorrowedBooks().contains(retorned));
    }

    @Test
    void searchBooks() {
        List<Book> result = libreria.searchBooks("El arte de la guerra", "Sun Tzu",200, 430);
        assertEquals(1,result.size());
        assertEquals("El arte de la guerra", result.get(0).getTitle());
    }

    @Test
    void searchBooksByAuthor() {
        List<Book> result = libreria.searchBooksByAuthor().get(autor1.getFullName());
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getBorrowedBooksByUser() {
        libreria.lendBook("9786074006216", "yavac@ebac-estudiantes.org.mx");
        List<String> results = libreria.getBorrowedBooksByUser("yavac@ebac-estudiantes.org.mx");
        assertEquals(1, results.size());
        assertEquals("El arte de la seduccion", results.get(0));
    }
}