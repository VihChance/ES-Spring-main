package com.example.spring.controller;

import com.example.spring.domain.user.User;
import com.example.spring.dto.LoginRequest;
import com.example.spring.dto.LoginResponse;
import com.example.spring.security.JwtUtilitario;
import com.example.spring.services.UserService;
import com.example.spring.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final UserService userService;
    private final JwtUtilitario jwtUtilitario;

    public AuthController(UserService userService, JwtUtilitario jwtUtilitario) {
        this.userService = userService;
        this.jwtUtilitario = jwtUtilitario;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request.getEmail(), request.getPassword());

            String token = jwtUtilitario.generateToken(
                    user.getEmail(),
                    user.getRole().name()
            );

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas");
        }
    }

}
