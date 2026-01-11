package com.example.spring.controller;

import com.example.spring.domain.Fase;
import com.example.spring.dto.CriarFaseDTO;
import com.example.spring.services.FaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fases")
@CrossOrigin(origins = "*")
public class FaseController {

    @Autowired private FaseService service;

    @PostMapping(consumes = "application/json")
    public Fase criar(@RequestBody CriarFaseDTO dto){
        return service.criar(dto.exercicioId(), dto.titulo(), dto.ordem());
    }

    @GetMapping("/exercicio/{exercicioId}")
    public java.util.List<Fase> listar(@PathVariable Long exercicioId){
        return service.listarPorExercicio(exercicioId);
    }

}
