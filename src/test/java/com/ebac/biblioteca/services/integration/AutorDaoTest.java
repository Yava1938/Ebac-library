package com.ebac.biblioteca.services.integration;

import com.ebac.biblioteca.dao.implementador.AutorImp;
import com.ebac.biblioteca.dao.implementador.UserImp;
import com.ebac.biblioteca.dao.interfaces.AutorDAO;
import com.ebac.biblioteca.dao.interfaces.AutorDAO;
import com.ebac.biblioteca.dto.Autor;
import com.ebac.biblioteca.dto.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutorDaoTest extends DatabaseTest{
    @Test
    void testIsertarEncontrarAuthor() throws SQLException {
        AutorDAO dao = new AutorImp(conn);

        Autor author = new Autor("Yael Vargas", "Mexicano");
        dao.save(author);

        assertTrue(author.getId_autor() > 0);
        Optional<Autor> resultado = dao.findById(author.getId_autor());
        assertTrue(resultado.isPresent());
        assertEquals("Yael Vargas", resultado.get().getNombre_autor());
    }
    @Test
    void testFindAll() throws SQLException{
        AutorDAO dao = new AutorImp(conn);
        dao.save(new Autor("Emilio Garcia", "Colombiano"));
        dao.save(new Autor("Gabriel Garcia", "Mexicano"));

        List<Autor> autores = dao.findAll();
        assertEquals(2, autores.size());

        dao.save(new Autor("Daniela escobedo", "Española"));
        List<Autor> autors2 = dao.findAll();
        assertEquals(3, autors2.size());

    }
    @Test
    void testUpdateUser() throws SQLException{
        AutorDAO dao = new AutorImp(conn);
        Autor autorUpd = new Autor("Robert Grenne",  "Estadounidense");
        dao.save(autorUpd);

        autorUpd.setNombre_autor("Gillermo Alvarez");
        dao.update(autorUpd);
        assertEquals("Gillermo Alvarez", autorUpd.getNombre_autor());

    }
    @Test
    void testDeleteUser() throws SQLException{
        AutorDAO dao = new AutorImp(conn);
        Autor autorDelete = new Autor("Jaime Perez", "Mexicano");
        dao.save(autorDelete);

        assertTrue(dao.deleteById(autorDelete.getId_autor()));
        assertTrue(dao.findById(autorDelete.getId_autor()).isEmpty());
    }

}
