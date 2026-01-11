package com.example.spring.controller;

import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.domain.Docente;
import com.example.spring.dto.CriarUnidadeCurricularDTO;
import com.example.spring.services.UnidadeCurricularService;
import com.example.spring.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public UnidadeCurricular criarUC(@RequestBody CriarUnidadeCurricularDTO dto) {
        Docente docente = docenteService.procurarPorId(dto.docenteId());
        if (docente == null) return null;

        return ucService.criarUnidadeCurricular(dto.nome(), docente);
    }


    @GetMapping
    public List<UnidadeCurricular> listarUCs() {
        return ucService.listarTodas();
    }

    @GetMapping("/docente/{docenteId}")
    public List<UnidadeCurricular> listarPorDocente(@PathVariable Long docenteId) {
        return ucService.listarPorDocente(docenteId);
    }
}
