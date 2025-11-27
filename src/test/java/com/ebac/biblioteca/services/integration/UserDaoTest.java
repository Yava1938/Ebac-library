package com.ebac.biblioteca.services.integration;

import com.ebac.biblioteca.dao.implementador.UserImp;
import com.ebac.biblioteca.dao.interfaces.UserDAO;
import com.ebac.biblioteca.dto.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest extends DatabaseTest{

    @Test
    void testIsertarEncontrarUser() throws SQLException{
        UserDAO dao = new UserImp(conn);

        User usr = new User("Elle Fanning", "Efanning@ebac.com", "Mexico123");
        dao.save(usr);

        assertTrue(usr.getId_usuario() > 0);
        Optional<User> resultado = dao.findById(usr.getId_usuario());
        assertTrue(resultado.isPresent());
        assertEquals("Elle Fanning", resultado.get().getNombre_usuario());
    }
    @Test
    void testFindAll() throws SQLException{
        UserDAO dao = new UserImp(conn);
        dao.save(new User("Usuario1", "user1@prueba.com","prueba"));
        dao.save(new User("Usuario2", "user2@prueba.com","prueba"));

        List<User> users = dao.findAll();
        assertEquals(2, users.size());

        dao.save(new User("Usuario2", "user2@prueba.com","prueba"));
        List<User> users2 = dao.findAll();
        assertEquals(3, users2.size());

    }
    @Test
    void testUpdateUser() throws SQLException{
        UserDAO dao = new UserImp(conn);
        User userUpd = new User("Elle Fanning", "Efanning@ebac.com", "Mexico123");
        dao.save(userUpd);

        userUpd.setEmail_usuario("Efannig-nuevo@ebac.com");
        dao.update(userUpd);
        assertEquals("Efannig-nuevo@ebac.com", userUpd.getEmail_usuario());

    }
    @Test
    void testDeleteUser() throws SQLException{
        UserDAO dao = new UserImp(conn);
        User userDelete = new User("Dakota Fanning", "Dfanning@ebac.com", "Mexico123");
        dao.save(userDelete);

        assertTrue(dao.delete(userDelete.getId_usuario()));
        assertTrue(dao.findById(userDelete.getId_usuario()).isEmpty());
    }

}
