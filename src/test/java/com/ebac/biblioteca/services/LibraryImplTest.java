package com.ebac.biblioteca.services;

import com.ebac.biblioteca.dao.implementador.AutorImp;
import com.ebac.biblioteca.dao.implementador.BookImp;
import com.ebac.biblioteca.dao.implementador.UserImp;
import com.ebac.biblioteca.dto.Autor;
import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;
import com.ebac.biblioteca.services.integration.DatabaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryImplTest extends DatabaseTest {

   @Test
    void testLendyReturnBook() throws SQLException{
       LibraryImpl libreria = new LibraryImpl(
               new BookImp(conn),
               new AutorImp(conn),
               new UserImp(conn)
       );

       Autor autor1 = new Autor("Patricio Luna", "Mexicano");
       libreria.addAutor(autor1);

       User usuario1 =  new User("Yael Vargas", "yava@ebac.com","Mexico123");
       libreria.addUser(usuario1);

       Book book1 = new Book("Expedientes secretos X", 1,1990, true, null);
       libreria.addBook(book1);

       libreria.lendBook(book1.getId_libro(), usuario1.getId_usuario());

       Book prestado = libreria.getBookById(book1.getId_libro()).get();
       assertFalse(prestado.isDisponible());
       assertEquals(usuario1.getId_usuario(), prestado.getId_usuario());

       libreria.returnBook(book1.getId_libro(), usuario1.getId_usuario());

       Book devuelto = libreria.getBookById(book1.getId_libro()).get();
       assertTrue(devuelto.isDisponible());
       assertNull(devuelto.getId_usuario());
   }
}