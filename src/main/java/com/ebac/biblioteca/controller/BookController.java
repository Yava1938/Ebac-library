package com.ebac.biblioteca.controller;


import com.ebac.biblioteca.dto.Book;
import com.ebac.biblioteca.dto.ResponseWrapper;
import com.ebac.biblioteca.services.AuthService;
import com.ebac.biblioteca.services.Library;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final Library library;
    private final AuthService authService;

    @PostMapping("/books")
    public ResponseEntity<ResponseWrapper<Book>> createBook(@RequestHeader("X-SESSION-ID") Long sessionId, @RequestBody Book book) {
        authService.validateSession(sessionId);
        Book saved = library.addBook(book);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), saved));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ResponseWrapper<Book>> getBookById(@RequestHeader("X-SESSION-ID") Long sessionId,@PathVariable Long id) {
        authService.validateSession(sessionId);
        return library.getBookById(id)
                .map(book -> ResponseEntity.ok(
                        new ResponseWrapper<>(200, "000", List.of(), book)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>(404, "004", List.of("Libro no encontrado"), null)));
    }

    @GetMapping("/books")
    public ResponseEntity<ResponseWrapper<List<Book>>> getAllBooks(@RequestHeader("X-SESSION-ID") Long sessionId) {
        authService.validateSession(sessionId);
        return ResponseEntity.ok( new ResponseWrapper<>(200, "000", List.of(), library.searchBooks()));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ResponseWrapper<Book>> updateBook(
            @RequestHeader("X-SESSION-ID") Long sessionId,
            @PathVariable Long id,
            @RequestBody Book book) {

        authService.validateSession(sessionId);
        book.setId(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "000", List.of(), library.updateBook(book)));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@RequestHeader("X-SESSION-ID") Long sessionId, @PathVariable Long id) {
        authService.validateSession(sessionId);
        library.deleteBook(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), null)
        );
    }


    @PostMapping("/books/{bookId}/lend/{userId}")
    public ResponseEntity<ResponseWrapper<Book>> lend(
            @RequestHeader("X-SESSION-ID") Long sessionId,
            @PathVariable("bookId") Long bookId,
            @PathVariable("userId") Long userId
    ) {
        authService.validateSession(sessionId);
        Book book = library.lendBook(bookId, userId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), book)
        );
    }

    @PostMapping("/books/{bookId}/return")
    public ResponseEntity<ResponseWrapper<Book>> returnBook(
            @RequestHeader("X-SESSION-ID") Long sessionId,
            @PathVariable Long bookId) {
        authService.validateSession(sessionId);
            Book book = library.returnBook(bookId);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(200, "000", List.of(), book)
            );
    }


}
