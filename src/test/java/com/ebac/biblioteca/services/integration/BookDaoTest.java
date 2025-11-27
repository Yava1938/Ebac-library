package com.ebac.biblioteca.services.integration;

import com.ebac.biblioteca.dao.implementador.BookImp;
import com.ebac.biblioteca.dao.implementador.UserImp;
import com.ebac.biblioteca.dao.interfaces.BookDAO;
import com.ebac.biblioteca.dto.Book;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookDaoTest extends DatabaseTest{
    @Test
    void testIsertarBook() throws SQLException {
        BookDAO dao = new BookImp(conn);

        Book bk = new Book("Clean architecture",1,2000, true, null);
        dao.save(bk);
        assertTrue(bk.getId_libro() > 0);

    }
    @Test
    void testFindAll() throws SQLException{
        BookDAO dao = new BookImp(conn);
        dao.save(new Book("Libro1",1, 2002, true,null));
        dao.save(new Book("Libro2",2, 2000, true,null));

        List<Book> libros = dao.findAll();
        assertEquals(2, libros.size());
    }
    @Test
    void testUpdateUser() throws SQLException{
        BookDAO dao = new BookImp(conn);
        Book bookUpd = new Book("libroPrueba1", 1,2002, true,null);
        dao.save(bookUpd);

        bookUpd.setNombre_libro("Prueba de fuego");
        dao.update(bookUpd);
        assertEquals("Prueba de fuego", bookUpd.getNombre_libro());

    }
    @Test
    void testDeleteBook() throws SQLException{
        BookDAO dao = new BookImp(conn);
        Book bookDelete = new Book("Amanecer", 3, 2006, true, null);
        dao.save(bookDelete);

        assertTrue(dao.delete(bookDelete.getId_libro()));
        assertTrue(dao.findById(bookDelete.getId_libro()).isEmpty());
    }

}
