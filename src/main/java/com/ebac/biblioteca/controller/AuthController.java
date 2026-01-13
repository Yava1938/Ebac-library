package com.ebac.biblioteca.controller;


import com.ebac.biblioteca.dto.LoginRequest;
import com.ebac.biblioteca.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {

        Long sessionId = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return Map.of(
                "statusCode", 200,
                "sessionId", sessionId,
                "message", "Login exitoso",
                "user", Map.of("username", request.getUsername())
        );
    }


    @PostMapping("/auth/logout")
    public Map<String, Object> logout(
            @RequestHeader("X-SESSION-ID") Long sessionId) {

        authService.logout(sessionId);

        return Map.of(
                "statusCode", 200,
                "message", "Logout exitoso"
        );
    }
}
