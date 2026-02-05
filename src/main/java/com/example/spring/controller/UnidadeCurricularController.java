package com.example.spring.controller;

import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.domain.Docente;
import com.example.spring.domain.user.User;
import com.example.spring.dto.CriarUnidadeCurricularDTO;
import com.example.spring.repository.DocenteRepository;
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
    private final DocenteRepository docenteRepository; // ← novo

    public UnidadeCurricularController(
            UnidadeCurricularService ucService,
            UserService userService,
            DocenteRepository docenteRepository
    ) {
        this.ucService = ucService;
        this.userService = userService;
        this.docenteRepository = docenteRepository;
    }

    // ─────────────────────────────
    // 1) ADMIN cria UCs para um docente
    // ─────────────────────────────
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UnidadeCurricular criarUC(@RequestBody CriarUnidadeCurricularDTO dto) {

        Docente docente = docenteRepository.findById(dto.docenteId())
                .orElseThrow(() -> new RuntimeException("Docente não encontrado"));

        return ucService.criarUnidadeCurricular(dto.nome(), docente);
    }

    // ─────────────────────────────
    // 2) DOCENTE vê só as SUAS UCs
    // ─────────────────────────────
    @PreAuthorize("hasRole('DOCENTE')")
    @GetMapping
    public List<UnidadeCurricular> listarMinhasUCs(Authentication authentication) {

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user.getDocente() == null) {
            throw new RuntimeException("User autenticado não é um docente");
        }

        return ucService.listarPorDocente(user.getDocente().getId());
    }

    // ─────────────────────────────
    // 3) Listar todas (para aluno ver, etc.)
    // ─────────────────────────────
    @PreAuthorize("hasAnyRole('ALUNO','DOCENTE','ADMIN')")
    @GetMapping("/todas")
    public List<UnidadeCurricular> listarTodas() {
        return ucService.listarTodas();
    }
}
