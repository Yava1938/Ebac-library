package com.ebac.biblioteca.services;


import com.ebac.biblioteca.dto.Author;
import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;
import com.ebac.biblioteca.entity.AuthorEntity;
import com.ebac.biblioteca.entity.BookEntity;
import com.ebac.biblioteca.entity.UserEntity;
import com.ebac.biblioteca.exceptions.BookNotAvailableException;
import com.ebac.biblioteca.repository.AuthorRepository;
import com.ebac.biblioteca.repository.BookRepository;
import com.ebac.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryImplTest{

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryImpl library;

    @Test
    void addBook() {
        Book dto = new Book(null, "Clean Code", 2008, true, 1L, null);

        AuthorEntity author = new AuthorEntity("Robert Martin");
        author.setId(1L);

        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));

        when(bookRepository.save(any(BookEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Book result = library.addBook(dto);

        assertNotNull(result);
        assertEquals("Clean Code", result.getNombre());
    }

    @Test
    void addBook_authorNotFound() {
        Book book = new Book();
        book.setAuthorId(99L);

        when(authorRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> library.addBook(book)
        );
        assertEquals("Autor no existe", exception.getMessage());
    }

    @Test
    void getBookById() {
        BookEntity entity = new BookEntity();
        entity.setId(1L);
        entity.setNombre("DDD");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        Optional<Book> result = library.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals("DDD", result.get().getNombre());
    }

    @Test
    void searchBooks() {
        when(bookRepository.findAll())
                .thenReturn(List.of(new BookEntity(), new BookEntity()));

        List<Book> result = library.searchBooks();

        assertEquals(2, result.size());
    }

    @Test
    void updateBook() {
        BookEntity entity = new BookEntity();
        entity.setId(1L);
        entity.setNombre("Old");

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        when(bookRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Book dto = new Book(1L, "New", 2024, true, 1L, null);

        Book result = library.updateBook(dto);

        assertEquals("New", result.getNombre());
    }

    @Test
    void deleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        library.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void lendBook() {
        BookEntity book = new BookEntity();
        book.setDisponible(true);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(new UserEntity("Juan")));
        when(bookRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Book result = library.lendBook(1L, 1L);

        assertFalse(result.isDisponible());
    }


    @Test
    void lendBook_notAvailable() {
        BookEntity book = new BookEntity();
        book.setDisponible(false);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        assertThrows(BookNotAvailableException.class,
                () -> library.lendBook(1L, 1L));
    }

    @Test
    void returnBook() {
        UserEntity user = new UserEntity("Ana");
        user.setId(1L);

        BookEntity book = new BookEntity();
        book.setDisponible(false);
        book.setUser(user);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));
        when(bookRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Book result = library.returnBook(1L);

        assertTrue(result.isDisponible());
    }

    @Test
    void addUser() {
        when(userRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        User result = library.addUser(new User(null, "Carlos"));

        assertEquals("Carlos", result.getNombre());
    }

    @Test
    void getUserById() {
        UserEntity entity = new UserEntity("Luis");
        entity.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        Optional<User> result = library.getUserById(1L);

        assertTrue(result.isPresent());
    }

    @Test
    void searchUsers() {
        when(userRepository.findAll())
                .thenReturn(List.of(new UserEntity(), new UserEntity()));

        assertEquals(2, library.searchUsers().size());
    }

    @Test
    void updateUser() {
        UserEntity entity = new UserEntity("Old");
        entity.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        when(userRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        User dto = new User(1L, "New");

        User result = library.updateUser(dto);

        assertEquals("New", result.getNombre());
    }

    @Test
    void deleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        library.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void addAuthor() {
        when(authorRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Author result = library.addAuthor(new Author(null, "Orwell"));

        assertEquals("Orwell", result.getNombre());
    }

    @Test
    void getAuthorById() {
        AuthorEntity entity = new AuthorEntity("Asimov");
        entity.setId(1L);

        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        assertTrue(library.getAuthorById(1L).isPresent());
    }

    @Test
    void searchAuthors() {
        when(authorRepository.findAll())
                .thenReturn(List.of(new AuthorEntity(), new AuthorEntity()));

        assertEquals(2, library.searchAuthors().size());
    }

    @Test
    void updateAuthor() {
        AuthorEntity entity = new AuthorEntity("Old");
        entity.setId(1L);

        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(entity));
        when(authorRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        Author dto = new Author(1L, "New");

        Author result = library.updateAuthor(dto);

        assertEquals("New", result.getNombre());
    }

    @Test
    void deleteAuthor() {
        when(authorRepository.existsById(1L)).thenReturn(true);

        library.deleteAuthor(1L);

        verify(authorRepository).deleteById(1L);
    }
}