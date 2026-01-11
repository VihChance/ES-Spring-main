package com.example.spring.controller;

import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.domain.Docente;
import com.example.spring.dto.CriarUnidadeCurricularDTO;
import com.example.spring.services.UnidadeCurricularService;
import com.example.spring.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ucs")
@CrossOrigin(origins = "*")
public class UnidadeCurricularController {

    @Autowired
    private UnidadeCurricularService ucService;

    @Autowired
    private DocenteService docenteService;

    // 🔐 só DOCENTE
    @PreAuthorize("hasRole('DOCENTE')")


    @PostMapping
    public UnidadeCurricular criarUC(
            @RequestBody CriarUnidadeCurricularDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName(); // vem do JWT

        Docente docente = docenteService.procurarPorEmail(email);

        return ucService.criarUnidadeCurricular(dto.nome(), docente);
    }

    // 🔐 só DOCENTE
    @PreAuthorize("hasRole('DOCENTE')")
    @GetMapping
    public List<UnidadeCurricular> listarMinhasUCs(Authentication authentication) {

        String email = authentication.getName();
        Docente docente = docenteService.procurarPorEmail(email);

        return ucService.listarPorDocente(docente.getId());
    }
}
