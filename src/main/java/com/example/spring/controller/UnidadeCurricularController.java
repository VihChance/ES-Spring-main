package com.example.spring.controller;

import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.domain.Docente;
import com.example.spring.domain.user.User;
import com.example.spring.dto.CriarUnidadeCurricularDTO;
import com.example.spring.services.UnidadeCurricularService;
import com.example.spring.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ucs")
@CrossOrigin(origins = "*")
public class UnidadeCurricularController {

    private final UnidadeCurricularService ucService;
    private final UserService userService;

    public UnidadeCurricularController(
            UnidadeCurricularService ucService,
            UserService userService
    ) {
        this.ucService = ucService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public UnidadeCurricular criarUC(
            @RequestBody CriarUnidadeCurricularDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        Docente docente;

        if (user.getRole().name().equals("ADMIN")) {
            throw new RuntimeException("ADMIN deve usar outro endpoint ou fornecer ID do docente.");
        } else if (user.getDocente() == null) {
            throw new RuntimeException("User autenticado não é um docente");
        } else {
            docente = user.getDocente();
        }

        return ucService.criarUnidadeCurricular(dto.nome(), docente);
    }


    //  só DOCENTE
    @PreAuthorize("hasAnyRole('DOCENTE', 'ADMIN')")
    @GetMapping
    public List<UnidadeCurricular> listarMinhasUCs(Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user.getDocente() == null) {
            throw new RuntimeException("User autenticado não é um docente");
        }

        return ucService.listarPorDocente(user.getDocente().getId());
    }

    // UnidadeCurricularController.java
    @PreAuthorize("hasAnyRole('ALUNO','DOCENTE','ADMIN')")
    @GetMapping("/todas")
    public List<UnidadeCurricular> listarTodas() {
        return ucService.listarTodas();
    }


}
