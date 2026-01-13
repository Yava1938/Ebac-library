package com.ebac.biblioteca.services;

import com.ebac.biblioteca.dto.Author;
import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.User;
import com.ebac.biblioteca.entity.AuthorEntity;
import com.ebac.biblioteca.entity.BookEntity;
import com.ebac.biblioteca.entity.LibraryEntity;
import com.ebac.biblioteca.entity.UserEntity;
import com.ebac.biblioteca.exceptions.BookNotAvailableException;
import com.ebac.biblioteca.exceptions.UserNotFoundException;
import com.ebac.biblioteca.mapper.AuthorMapper;
import com.ebac.biblioteca.mapper.BookMapper;
import com.ebac.biblioteca.mapper.UserMapper;
import com.ebac.biblioteca.repository.AuthorRepository;
import com.ebac.biblioteca.repository.BookRepository;
import com.ebac.biblioteca.repository.LibraryRepository;
import com.ebac.biblioteca.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryImpl implements Library {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    @Override
    public Book addBook(Book book) {

        AuthorEntity author = authorRepository.findById(book.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Autor no existe"));

        LibraryEntity defaultLibrary = libraryRepository.findById(1L).orElseThrow();
        BookEntity entity = BookMapper.toEntity(book, author, defaultLibrary);
        return BookMapper.toDto(bookRepository.save(entity));
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toDto);
    }

    @Override
    public List<Book> searchBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Book updateBook(Book book) {
        BookEntity entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RuntimeException("Libro no existe"));

        entity.setNombre(book.getNombre());
        entity.setAnio(book.getAnio());

        return BookMapper.toDto(bookRepository.save(entity));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Libro no existe");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Book lendBook(Long bookId, Long userId) {

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Libro no existe"));

        if (!book.isDisponible()) {
            throw new BookNotAvailableException("Libro no disponible");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("Usuario no encontrado"));

        book.setDisponible(false);
        book.setUser(user);

        return BookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public Book returnBook(Long bookId) {

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Libro no existe"));

        if (book.isDisponible()) {
            throw new RuntimeException("El libro no está prestado");
        }

        if (book.getUser() == null) {
            throw new RuntimeException("El usuario no tiene este libro");
        }

        book.setDisponible(true);
        book.setUser(null);

        return BookMapper.toDto(bookRepository.save(book));
    }


    @Override
    public User addUser(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        return UserMapper.toDto(userRepository.save(entity));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto);
    }

    @Override
    public List<User> searchUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User updateUser(User user) {
        UserEntity entity = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException ("Usuario no encontrado"));

        entity.setNombre(user.getNombre());
        return UserMapper.toDto(userRepository.save(entity));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }


    @Override
    public Author addAuthor(Author author) {
        AuthorEntity entity = AuthorMapper.toEntity(author);
        return AuthorMapper.toDto(authorRepository.save(entity));
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper::toDto);
    }

    @Override
    public List<Author> searchAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Author updateAuthor(Author author) {
        AuthorEntity entity = authorRepository.findById(author.getId())
                .orElseThrow(() -> new RuntimeException("Autor no existe"));

        entity.setNombre(author.getNombre());
        return AuthorMapper.toDto(authorRepository.save(entity));
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Autor no existe");
        }
        authorRepository.deleteById(id);
    }
}