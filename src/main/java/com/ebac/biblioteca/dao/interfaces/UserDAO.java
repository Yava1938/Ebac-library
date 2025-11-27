package com.ebac.biblioteca.dao.interfaces;

import com.ebac.biblioteca.dto.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User save(User user) throws SQLException;
    Boolean update(User user) throws SQLException;
    Boolean delete(int id_user) throws SQLException;
    Optional<User> findById(int id) throws SQLException;
    List<User> findAll() throws SQLException;
}
