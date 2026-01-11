package com.example.spring.controller;

import com.example.spring.domain.Exercicio;
import com.example.spring.dto.CriarExercicioDTO;
import com.example.spring.services.ExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercicios")
@CrossOrigin(origins = "*")
public class ExercicioController {

    @Autowired
    private ExercicioService exercicioService;

    @PostMapping
    public Exercicio criar(@RequestBody CriarExercicioDTO dto) {
        return exercicioService.criarExercicio(dto.ucId(), dto.titulo());
    }


    @GetMapping("/uc/{ucId}")
    public List<Exercicio> listarPorUC(@PathVariable Long ucId) {
        return exercicioService.listarPorUnidadeCurricular(ucId);
    }

    @GetMapping("/{id}")
    public Exercicio getExercicio(@PathVariable Long id) {
        return exercicioService.procurarPorId(id);
    }
}
