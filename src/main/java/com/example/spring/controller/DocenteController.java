package com.example.spring.controller;

import com.example.spring.domain.Docente;
import com.example.spring.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    // 🔐 DOCENTE vê apenas o seu próprio perfil

    @PreAuthorize("hasRole('DOCENTE')")


    @GetMapping("/me")
    public Docente meuPerfil(Authentication authentication) {

        String email = authentication.getName(); // vem do JWT (subject)

        return docenteService.procurarPorEmail(email);
    }


}
