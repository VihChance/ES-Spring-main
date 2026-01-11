package com.example.spring.controller;

import com.example.spring.dto.LoginRequest;
import com.example.spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final UserService service;

    public LoginController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        boolean ok = service.login(
                request.getEmail(),
                request.getPassword()
        );

        if (!ok) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas");
        }

        return ResponseEntity.ok("Login válido");
    }
}
