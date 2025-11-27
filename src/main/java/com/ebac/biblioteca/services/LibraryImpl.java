package com.ebac.biblioteca.services;

import com.ebac.biblioteca.dao.interfaces.AutorDAO;
import com.ebac.biblioteca.dao.interfaces.BookDAO;
import com.ebac.biblioteca.dao.interfaces.UserDAO;
import com.ebac.biblioteca.dto.Autor;

import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;

import java.sql.SQLException;
import java.util.*;


public class LibraryImpl implements Library{
    private final BookDAO bookDAO;
    private final AutorDAO autorDAO;
    private final UserDAO userDAO;

    public LibraryImpl(BookDAO bookDAO, AutorDAO autorDAO, UserDAO userDAO) {
        this.bookDAO = bookDAO;
        this.autorDAO = autorDAO;
        this.userDAO = userDAO;
    }


    @Override
    public Book addBook(Book book) throws SQLException {
        return bookDAO.save(book);
    }

    @Override
    public Optional<Book> getBookById(int id) throws SQLException {
        return bookDAO.findById(id);
    }

    @Override
    public List<Book> searchBooks() throws SQLException {
        return bookDAO.findAll();
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        if (bookDAO.update(book)){
            System.out.println("Libro actualizado correctamente");
        }else{
            System.out.println("No se logro actualizar el libro");
        }
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        if(bookDAO.delete(id)){
            System.out.println("Libro eliminado correctamente");
        }else{
            System.out.println("No se pudo eliminar el libro" );
        }
    }

    @Override
    public void lendBook(int id_libro, int id_usuario) throws SQLException {
        Optional<Book> bq_libro = bookDAO.findById(id_libro);
        if (bq_libro.isEmpty()){
            throw new SQLException("El libro: " + id_libro + " no existe");
        }
        Book book = bq_libro.get();
        if (!book.isDisponible()){
            throw new SQLException("El libro: " + book.getNombre_libro() + " no se encuentra disponible");
        }
        Optional<User> usr =  userDAO.findById(id_usuario);
        if (usr.isEmpty()){
            throw new SQLException("Usuario: " + id_usuario + " no existe");
        }
        User user = usr.get();
        book.setDisponible(false);
        book.setId_usuario(user.getId_usuario());
        bookDAO.update(book);
        System.out.println("Libro prestado correctamente");
    }

    @Override
    public void returnBook(int id_libro, int id_usuario) throws SQLException {
        Optional<Book> bookPrestado = bookDAO.findById(id_libro);
        if (bookPrestado.isEmpty()){
            throw new SQLException("El libro: " + id_libro + " no existe");
        }
        Book bookCuestion = bookPrestado.get();

        if (bookCuestion.isDisponible()){
            throw new SQLException("El libro: " + bookCuestion.getNombre_libro() + " no ha sido prestado");
        }

        Optional<User> userCliente = userDAO.findById(id_usuario);
        if (userCliente.isEmpty()){
            throw new SQLException("No existe el cliente: " + id_usuario);
        }
        User userCuestion = userCliente.get();
        if (bookCuestion.getId_usuario() == null ||
                !bookCuestion.getId_usuario().equals(userCuestion.getId_usuario())){
            throw new SQLException("El usuario: " + userCuestion.getNombre_usuario() +
                    " no tiene prestado el libro: " + bookCuestion.getNombre_libro());
        }

        bookCuestion.setDisponible(true);
        bookCuestion.setId_usuario(null);
        bookDAO.update(bookCuestion);
        System.out.println("Libro devuelto correctamente");

    }

    @Override
    public User addUser(User user) throws SQLException {
        return  userDAO.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) throws SQLException {
        ;
        return userDAO.findById(id);
    }

    @Override
    public List<User> searchUsers() throws SQLException {
        return userDAO.findAll();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        if(userDAO.update(user)){
            System.out.println("Usuario actualizado exitosamente");
        }else{
            System.out.println("No se pudo actualizar el usuario");
        }
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        if(userDAO.delete(id)){
            System.out.println("Usuario eliminado exitosamente");
        }else{
            System.out.println("No se pudo eliminar al usuario");
        }
    }

    @Override
    public Autor addAutor(Autor autor) throws SQLException {
        return autorDAO.save(autor);
    }

    @Override
    public Optional<Autor> getAutorById(int id) throws SQLException {
        return  autorDAO.findById(id);
    }

    @Override
    public List<Autor> searchAutor() throws SQLException {

        return  autorDAO.findAll();
    }

    @Override
    public void updateAutor(Autor autor) throws SQLException {
        autorDAO.update(autor);
    }

    @Override
    public void deleteAutor(int id) throws SQLException {
        if(autorDAO.deleteById(id)){
            System.out.println("Autor eliminado exitosamente");
        }else{
            System.out.println("No se pudo eliminar al autor");
        }

    }
}
