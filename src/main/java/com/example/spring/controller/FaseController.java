package com.example.spring.controller;

import com.example.spring.domain.Fase;
import com.example.spring.dto.CriarFaseDTO;
import com.example.spring.services.FaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fases")
@CrossOrigin(origins = "*")
public class FaseController {

    private final FaseService service;

    public FaseController(FaseService service) {
        this.service = service;
    }

    // DOCENTE cria fases
    @PreAuthorize("hasRole('DOCENTE')")
    @PostMapping(consumes = "application/json")
    public Fase criar(@RequestBody CriarFaseDTO dto) {
        return service.criar(dto.exercicioId(), dto.titulo(), dto.ordem());
    }

    // ALUNO/DOCENTE/ADMIN podem ver as fases de um exercício
    @PreAuthorize("hasAnyRole('ALUNO','DOCENTE','ADMIN')")
    @GetMapping("/exercicio/{exercicioId}")
    public List<Fase> listar(@PathVariable Long exercicioId) {
        return service.listarPorExercicio(exercicioId);
    }
}
