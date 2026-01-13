package com.ebac.biblioteca.controller;


import com.ebac.biblioteca.dto.Author;
import com.ebac.biblioteca.dto.ResponseWrapper;
import com.ebac.biblioteca.services.AuthService;
import com.ebac.biblioteca.services.Library;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {


    private final Library library;
    private final AuthService authService;

    @PostMapping("/autores")
    public ResponseEntity<ResponseWrapper<Author>> create(@RequestHeader("X-SESSION-ID") Long sessionId, @RequestBody Author author) {
        authService.validateSession(sessionId);
        Author saved = library.addAuthor(author);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), saved)
        );
    }

    @GetMapping("/autores/{id}")
    public ResponseEntity<ResponseWrapper<Author>> getById(@RequestHeader("X-SESSION-ID") Long sessionId, @PathVariable Long id) {
        authService.validateSession(sessionId);
        return library.getAuthorById(id)
                .map(author -> ResponseEntity.ok(
                        new ResponseWrapper<>(200, "000", List.of(), author)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>(404, "404", List.of("Autor no encontrado"), null)));
    }

    @PutMapping("/autores/{id}")
    public ResponseEntity<ResponseWrapper<Author>> update(
            @RequestHeader("X-SESSION-ID") Long sessionId,
            @PathVariable Long id,
            @RequestBody Author author) {

        authService.validateSession(sessionId);
        author.setId(id);
        Author updated = library.updateAuthor(author);

        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), updated)
        );
    }

    @GetMapping("/autores")
    public ResponseEntity<ResponseWrapper<List<Author>>> getAll(@RequestHeader("X-SESSION-ID") Long sessionId) {
        authService.validateSession(sessionId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), library.searchAuthors())
        );
    }

    @DeleteMapping("/autores/{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@RequestHeader("X-SESSION-ID") Long sessionId, @PathVariable Long id) {
        authService.validateSession(sessionId);
        library.deleteAuthor(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), null)
        );
    }
}
