package com.ebac.biblioteca.dao.interfaces;

import com.ebac.biblioteca.dto.Autor;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AutorDAO {
    Autor save(Autor autor) throws SQLException;
    Autor update(Autor autor) throws SQLException;
    Boolean deleteById(int id_autor) throws SQLException;
    Optional<Autor> findById(int id_autor) throws SQLException;
    List<Autor> findAll() throws SQLException;
}
