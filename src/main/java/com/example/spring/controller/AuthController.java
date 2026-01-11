package com.example.spring.controller;

import com.example.spring.domain.user.User;
import com.example.spring.dto.LoginRequest;
import com.example.spring.dto.LoginResponse;
import com.example.spring.security.JwtUtilitario;
import com.example.spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        boolean ok = userService.login(request.getEmail(), request.getPassword());

        if (!ok) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas");
        }

        User u = userService.findByEmail(request.getEmail()); // vamos criar já
        String token = JwtUtilitario.generateToken(u.getEmail(), u.getRole().name());

        return ResponseEntity.ok(new LoginResponse(token));
    }

}
