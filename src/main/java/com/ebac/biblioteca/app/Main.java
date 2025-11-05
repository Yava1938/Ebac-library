package com.ebac.biblioteca.app;

import com.ebac.biblioteca.modelos.Author;
import com.ebac.biblioteca.modelos.Book;
import com.ebac.biblioteca.modelos.User;
import com.ebac.biblioteca.services.LibraryImpl;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
        private static final LibraryImpl libreria = new LibraryImpl();
        private static final Scanner scanner = new Scanner(System.in);

        private static final Map<String, Author> authors = new HashMap<>();
        private static final Map<String, Book> books = new HashMap<>();
        private static final Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Bienvenido a la Biblioteca Ebac para todos....");

        boolean activo = true;
        while (activo){
            showMenu();
            int option = leervalor("Selecciona una opcion...");
            switch (option) {
                case 1 -> crearAutor();
                case 2 -> crearLibro();
                case 3 -> crearUsuario();
                case 4 -> agregarLibroABiblioteca();
                case 5 -> agregarUsuarioABiblioteca();
                case 6 -> asignarLibroAAutor();
                case 7 -> prestarLibro();
                case 8 -> devolverLibro();
                case 9 -> mostrarLibrosPorAutor();
                case 10 -> buscarLibroPorTitulo();
                case 11 -> verLibros();
                case 12 -> verUsuarios();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    activo = false;
                }
                default -> System.out.println("Opción no válida, intenta nuevamente.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n=== Biblioteca EBAC ===");
        System.out.println("1. Crear autor");
        System.out.println("2. Crear libro");
        System.out.println("3. Crear usuario");
        System.out.println("4. Agregar libro a biblioteca");
        System.out.println("5. Agregar usuario a biblioteca");
        System.out.println("6. Asignar libro a autor");
        System.out.println("7. Prestar libro");
        System.out.println("8. Devolver libro");
        System.out.println("9. Mostrar libros por autor");
        System.out.println("10. Buscar libro por título");
        System.out.println("11. Ver libros registrados/pendientes");
        System.out.println("12. Ver usuarios registrados/pendientes");
        System.out.println("0. Salir");
    }

    private static int leervalor(String mensaje) {
        System.out.println(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("valor no valido: ");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }

    private static void crearAutor(){
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Biografía: ");
        String bio = scanner.nextLine();

        Author autor = new Author(nombre, apellido, bio);
        authors.put(autor.getFullName(), autor);
        System.out.println("Autor creado correctamente: " + autor.getFullName());
    }
    private static void crearLibro(){
        if (authors.isEmpty()) {
            System.out.println("Primero crea al menos un autor antes de crear libros.");
            return;
        }
        System.out.print("Titulo: ");
        String title = scanner.nextLine();

        int year = leervalor("Año de publicación: ");

        System.out.print("Autores disponibles: ");
        authors.values().forEach(autor -> System.out.println("- "+ autor.getFullName()));
        System.out.println("Autor (nombre completo): ");
        String autorName = scanner.nextLine();
        Author author = authors.get(autorName);
        if (author == null){
            System.out.println("Autor no existente");
            return;
        }
        System.out.println("ISBN: ");
        String isbn = scanner.nextLine();
        Book book = new Book(title,year,author,isbn);
        books.put(isbn,book);
        System.out.println("Libro creado correctamente: " + book.getTitle());
    }

    private static void crearUsuario(){
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        User user = new User(nombre, email, password);
        users.put(email, user);
        System.out.println("Usuario creado correctamente: " + user.getName());
    }

    private static void agregarLibroABiblioteca(){
        List<Book> pendientes = books.values().stream()
                .filter(book -> libreria.getBookByISBN(book.getISBN()).isEmpty())
                .collect(Collectors.toList());
        if (pendientes.isEmpty()){
            System.out.println("No hay libros pendientes por agregar...");
            return;
        }
        System.out.println("Libros pendientes por agregar: ");
        pendientes.forEach(book -> System.out.println("- " + book.getTitle() + " (" + book.getISBN() + ")"));
        System.out.println("ISBN del libro por agregar");
        String isbn = scanner.nextLine();
        Book libro = books.get(isbn);
        if (libro== null){
            System.out.println("Libro no encontrado...");
            return;
        }
        libreria.addBook(libro);
        System.out.println("Libro agregado correctamente: " + libro.getTitle());
    }

    private static void agregarUsuarioABiblioteca(){
        List<User> pendientes = users.values().stream()
                .filter(usr -> libreria.getUserByEmail(usr.getEmail()).isEmpty())
                .collect(Collectors.toList());
        if(pendientes.isEmpty()){
            System.out.println("No hay usuario pendientes por agregar a biblioteca....");
            return;
        }
        System.out.println("Usuarios pendientes:");
        pendientes.forEach(user -> System.out.println("-"+user.getName() + " (" + user.getEmail() + ")"));
        System.out.println("Email del usuario a agregar:");
        String email = scanner.nextLine();
        User user =  users.get(email);
        if (user == null){
            System.out.println("Usuario no encontrado");
            return;
        }
        libreria.addUser(user);
        System.out.println("Usuario agregado correctamente a la libreria....");

    }

    private static void asignarLibroAAutor () {
        List<Book> noAutor =  books.values().stream()
                .filter(book -> book.getAuthor() == null)
                .collect(Collectors.toList());
        if(noAutor.isEmpty()){
            System.out.println("No hay libros por asignar a autor...");
            return;
        }
        System.out.println("Libros con autores pendientes:");
        noAutor.forEach(book -> System.out.println("-"+book.getTitle() + " (" + book.getISBN() + ")"));

        System.out.println("ISBN del libro:");
        String isbnBook = scanner.nextLine();
        System.out.println("Nombre completo del autor:");
        String authorName =  scanner.nextLine();
        Author author = authors.get(authorName);
        Book book = books.get(isbnBook);
        if(author == null || book == null){
            System.out.println("Autor o libro no encontrado");
            return;
        }
        author.addBook(book);
        book.setAuthor(author);
        System.out.println("Libro asignado correctamente a su autor....");
    }
    private static void prestarLibro(){
        List<Book> disponibles =  books.values().stream()
                .filter(Book::isAvailabe)
                .collect(Collectors.toList());

        if (disponibles.isEmpty()){
            System.out.println("No hay libros disponibles por prestar...");
            return;
        }
        System.out.println("Libros disponibles:");
        disponibles.forEach(book -> System.out.println("-"+book.getTitle() + " ("+book.getISBN()+")"));
        System.out.println("ISBN del libro a prestar: ");
        String isbnBook = scanner.nextLine();

        List<User> usersDisponibles =  libreria.getAllUsers();
        if(usersDisponibles.isEmpty()){
            System.out.println("No hay usuario registrados en la biblioteca");
            return;
        }
        System.out.println("Usuarios registrados en la biblioteca:");
        usersDisponibles.forEach(user -> System.out.println("-"+user.getName() + "("+user.getEmail()+")"));
        System.out.println("Email del usuario:");
        String emailUser = scanner.nextLine();

        try{
            libreria.lendBook(isbnBook, emailUser);
            System.out.println("Libro prestado correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void devolverLibro(){
        System.out.println("Usuario con libros prestados:");
        List<User> prestados =  libreria.getAllUsers().stream()
                .filter(usr-> !usr.getBorrowedBooks().isEmpty())
                .collect(Collectors.toList());

        if(prestados.isEmpty()){
            System.out.println("No hay usuarios con libros prestados");
            return;
        }
        prestados.forEach(user -> System.out.println("-" + user.getName() + " ("+ user.getEmail()+")"));
        System.out.println("Email del usuario: ");
        String emailUsr = scanner.nextLine();

        User user = libreria.getUserByEmail(emailUsr).orElse(null);
        if (user == null){
            System.out.println("Usuario no encontrado");
            return;
        }
        if( user.getBorrowedBooks().isEmpty()){
            System.out.println("El usuario no debe ni un libro");
            return;
        }
        System.out.println("Libros que puede devolver:");
        user.getBorrowedBooks().forEach(userBook -> System.out.println("-" + userBook.getTitle() + " ("+ userBook.getISBN()+")"));
        System.out.println("ISBN del libro a devolver:");
        String isbn = scanner.nextLine();

        try{
            libreria.returnBook(isbn,user.getEmail());
            System.out.println("Libro devuleto correctamente....");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void mostrarLibrosPorAutor(){
        System.out.println("Nombre completo del autor:");
        String autorName = scanner.nextLine();
        Map<String, List<Book>> librosAutor = libreria.searchBooksByAuthor();
        List<Book> libros =  librosAutor.get(autorName);

        if(libros == null || libros.isEmpty()){
            System.out.println("No hay libros registrados para este autor");
            return;
        }
        System.out.println("Libros de: " + autorName +": ");
        libros.forEach(book -> System.out.println("-"+book.getTitle()+" ("+book.getISBN()+") "));
    }

    private static void buscarLibroPorTitulo(){
        System.out.println("Libro a buscar: ");
        String bookName = scanner.nextLine();

        List<Book> libros = libreria.searchBookByTitle(bookName);
        if(libros == null || libros.isEmpty()){
            System.out.println("No se encontraron resultados con el nombre de " + bookName);
            return;
        }
        System.out.println("Resultados: ");
        libros.forEach(libro -> System.out.println("-"+libro.getTitle() + " ("+ libro.getISBN()+") "));

    }

    private static void verLibros(){
        System.out.println("** Listado de libros **");
        List<Book> registrados = libreria.searchBooks(null,null,null,null);
        if (registrados.isEmpty()){
            System.out.println("No hay libros registrados en la Biblioteca Ebac");
        }else{
            System.out.println("** Registrados **");
            registrados.forEach(book -> {
                System.out.println("-"+book.getTitle() +
                        "   |Autor: "+ (book.getAuthor() != null ? book.getAuthor().getFullName() : "Sin autor") +
                        "   |ISBN: "+book.getISBN()+
                        "   |Disponible: "+ (book.isAvailabe()? "si": "no"));
            });
            List<Book> pendientes =  books.values().stream()
                    .filter(book -> libreria.getBookByISBN(book.getISBN()).isEmpty())
                    .collect(Collectors.toList());
            if (pendientes.isEmpty()){
                System.out.println("No hay libros pendientes por registrar");
            }else{
                System.out.println("**  Pendientes por registrar    **");
                pendientes.forEach(book ->{
                        System.out.println("-"+book.getTitle() +
                        "   |Autor: "+ (book.getAuthor() != null ? book.getAuthor().getFullName() : "Sin autor") +
                        "   |ISBN: "+book.getISBN()+
                        "   |Disponible: "+ (book.isAvailabe()? "si": "no"));
            });
            }
        }
    }

    private static void verUsuarios(){
        System.out.println("** Listado de usuarios **");

        List<User> registrados = libreria.getAllUsers();
        if (registrados.isEmpty()) {
            System.out.println("No hay usuarios registrados en la biblioteca.");
        } else {
            System.out.println("Usuarios registrados:");
            registrados.forEach(user ->
                    System.out.println("- " + user.getName() +
                            " | Email: " + user.getEmail() +
                            " | Libros prestados: " + user.getBorrowedBooks().size()));
        }

        List<User> pendientes = users.values().stream()
                .filter(user -> libreria.getUserByEmail(user.getEmail()).isEmpty())
                .collect(Collectors.toList());
        if (pendientes.isEmpty()) {
            System.out.println("No hay usuarios pendientes por registrar.");
        } else {
            System.out.println("Usuarios pendientes por registrar:");
            pendientes.forEach(user ->
                    System.out.println("- " + user.getName() +
                            " | Email: " + user.getEmail()));
        }
    }
}
