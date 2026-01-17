package com.example.spring.controller;

import com.example.spring.domain.Docente;
import com.example.spring.domain.user.User;
import com.example.spring.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    private final UserService userService;

    public DocenteController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('DOCENTE')")
    @GetMapping("/me")
    public Docente meuPerfil(Authentication authentication) {

        String email = authentication.getName(); // vem do JWT

        User user = userService.findByEmail(email);

        if (user.getDocente() == null) {
            throw new RuntimeException("User não tem Docente associado");
        }

        return user.getDocente();
    }
}

