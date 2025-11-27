package com.ebac.biblioteca.app;

import com.ebac.biblioteca.configuracion.MyConnection;
import com.ebac.biblioteca.dao.implementador.AutorImp;
import com.ebac.biblioteca.dao.implementador.BookImp;
import com.ebac.biblioteca.dao.implementador.UserImp;
import com.ebac.biblioteca.dto.Autor;
import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;
import com.ebac.biblioteca.services.Library;
import com.ebac.biblioteca.services.LibraryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Bienvenido a la Biblioteca Ebac para todos....");

            Scanner scanner = new Scanner(System.in);

            Connection conn = MyConnection.getConnection();
            BookImp bookDaoImp = new BookImp(conn);
            AutorImp autorDaoImp = new AutorImp(conn);
            UserImp userDaoImp = new UserImp(conn);

            LibraryImpl libreria = new LibraryImpl(bookDaoImp,autorDaoImp,userDaoImp);
            int opcion;
            do{
                System.out.println("\n=== Biblioteca EBAC ===");
                System.out.println("1. Crear autor");
                System.out.println("2. Crear libro");
                System.out.println("3. Crear usuario");
                System.out.println("4. Prestar libro");
                System.out.println("5. Devolver libro");
                System.out.println("6. Mostrar todos los libros");
                System.out.println("7. Mostrar todos los usuarios");
                System.out.println("8. Mostrar todos los autores");
                System.out.println("0. Salir");
                System.out.println("Seleccione una opcion:");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion){
                    case 1 -> crearAutor(libreria, scanner);
                    case 2 -> crearLibro(libreria, scanner);
                    case 3 -> crearUsuario(libreria, scanner);
                    case 4 -> prestarLibro(libreria, scanner);
                    case 5 -> devolverLibro(libreria, scanner);
                    case 6 -> verLibros(libreria);
                    case 7 -> verUsuarios(libreria);
                    case 8 -> verAutores(libreria);
                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opcion no valida");
                }

            }while (opcion != 0);
            scanner.close();
            MyConnection.closeConnection();

        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
        }
    }



    private static void crearAutor(Library library, Scanner scan) throws SQLException {
        System.out.println("Registrar nuevo autor...");

        System.out.print("Nombre del autor: ");
        String nombre_autor = scan.nextLine();

        System.out.print("Nacionalidad: ");
        String nacionalidad = scan.nextLine();

        Autor autor = new Autor(nombre_autor,nacionalidad);
        library.addAutor(autor);

        System.out.println("Autor creado correctamente: " + autor.getNombre_autor());
    }
    private static void crearLibro(Library library, Scanner scan) throws SQLException {
        System.out.println("Registrar un nuevo libro...");

        System.out.print("nombre del libro: ");
        String nombre_libro = scan.nextLine();

        System.out.print("id del autor: ");
        int id_autor = Integer.parseInt(scan.nextLine());

        System.out.println("año de publicacion");
        int anio = Integer.parseInt(scan.nextLine());

        Book newBook = new Book(nombre_libro,id_autor,anio, true,null);
        library.addBook(newBook);
        System.out.println("Libro registrado exitosamente!");

    }

    private static void crearUsuario(Library library, Scanner scan) throws SQLException {
        System.out.print("Nombre usuario: ");
        String nombre_usuario = scan.nextLine();
        System.out.print("Correo electrónico: ");
        String email_usuario = scan.nextLine();
        System.out.print("Contraseña: ");
        String password_usuario = scan.nextLine();

        User newUser = new User(nombre_usuario, email_usuario, password_usuario);
        library.addUser(newUser);
        System.out.println("Usuario creado correctamente: " + newUser.getNombre_usuario());
    }

    private static void prestarLibro(Library library, Scanner scan) throws SQLException {
        System.out.println("Prestar libro...");
        System.out.println("Id libro:");
        int id_libro = Integer.parseInt(scan.nextLine());
        System.out.println("Id usuario:");
        int id_usuario = Integer.parseInt(scan.nextLine());

        library.lendBook(id_libro, id_usuario);
        System.out.println("Se prestó libro exitosamente...");
    }

    private static void devolverLibro(Library library, Scanner scan) throws SQLException {
        System.out.println("Recibiendo devuelta libro....");
        System.out.println("Id libro:");
        int id_libro = Integer.parseInt(scan.nextLine());
        System.out.println("Id usuario:");
        int id_usuario = Integer.parseInt(scan.nextLine());

        library.returnBook(id_libro, id_usuario);
        System.out.println("Se recibio libro exitosamente...");
    }



    private static void verLibros(Library library) throws SQLException {
        System.out.println("Consultando libros....");
        List<Book> books = library.searchBooks();
        if (books.isEmpty()){
            System.out.println("No hay libros registrados.");
        }else {
            books.forEach(System.out::println);
        }

    }

    private static void verUsuarios(Library library) throws SQLException {
        System.out.println("Consultando usuarios...");
        List<User> usuarios = library.searchUsers();
        if (usuarios.isEmpty()){
            System.out.println("No hay usuarios registrados.");
        }else {
            usuarios.forEach(System.out::println);
        }
    }
    private static void verAutores(Library library) throws SQLException {
        System.out.println("Consultando autores...");
        List<Autor> autores = library.searchAutor();
        if (autores.isEmpty()){
            System.out.println("No hay autores registrados.");
        }else {
            autores.forEach(System.out::println);
        }
    }
}
