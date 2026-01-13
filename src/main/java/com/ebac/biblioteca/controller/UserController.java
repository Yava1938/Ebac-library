package com.ebac.biblioteca.controller;


import com.ebac.biblioteca.dto.ResponseWrapper;
import com.ebac.biblioteca.dto.User;
import com.ebac.biblioteca.services.AuthService;
import com.ebac.biblioteca.services.Library;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Library library;
    private final AuthService authService;


    @PostMapping("/users")
    public ResponseEntity<ResponseWrapper<User>> create(@RequestHeader("X-SESSION-ID") Long sessionId, @RequestBody User user) {
        authService.validateSession(sessionId);
        User saved = library.addUser(user);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), saved)
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<User>> getById(@RequestHeader("X-SESSION-ID") Long sessionId, @PathVariable Long id) {
        authService.validateSession(sessionId);
        return library.getUserById(id)
                .map(user -> ResponseEntity.ok(
                        new ResponseWrapper<>(200, "000", List.of(), user)
                ))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseWrapper<>(404, "404", List.of("Usuario no encontrado"), null)));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseWrapper<List<User>>> getAll(@RequestHeader("X-SESSION-ID") Long sessionId) {
        authService.validateSession(sessionId);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), library.searchUsers())
        );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<User>> update(
            @RequestHeader("X-SESSION-ID") Long sessionId,
            @PathVariable Long id,
            @RequestBody User user) {

        authService.validateSession(sessionId);
        user.setId(id);
        User updated = library.updateUser(user);

        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), updated)
        );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<Void>> delete(@RequestHeader("X-SESSION-ID") Long sessionId, @PathVariable Long id) {
        authService.validateSession(sessionId);
        library.deleteUser(id);
        return ResponseEntity.ok(
                new ResponseWrapper<>(200, "000", List.of(), null)
        );
    }
}
