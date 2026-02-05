package com.example.spring.controller;

import com.example.spring.domain.Exercicio;
import com.example.spring.dto.CriarExercicioDTO;
import com.example.spring.services.ExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
@CrossOrigin(origins = "*")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    // ─────────── Criar exercício (DOCENTE) ───────────
    @PreAuthorize("hasRole('DOCENTE')")
    @PostMapping
    public ResponseEntity<Exercicio> criar(@RequestBody CriarExercicioDTO dto) {
        Exercicio ex = exercicioService.criarExercicio(dto.ucId(), dto.titulo());
        return ResponseEntity.status(HttpStatus.CREATED).body(ex);
    }

    // ─────────── Listar por UC (aluno/docente/admin) ───────────
    @PreAuthorize("hasAnyRole('ALUNO','DOCENTE','ADMIN')")
    @GetMapping("/uc/{ucId}")
    public List<Exercicio> listarPorUC(@PathVariable Long ucId) {
        return exercicioService.listarPorUnidadeCurricular(ucId);
    }

    // ─────────── Obter um exercício ───────────
    @PreAuthorize("hasAnyRole('ALUNO','DOCENTE','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Exercicio> getExercicio(@PathVariable Long id) {
        Exercicio ex = exercicioService.procurarPorId(id);
        if (ex == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ex);
    }
}
