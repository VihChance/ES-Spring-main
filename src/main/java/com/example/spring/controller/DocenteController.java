package com.example.spring.controller;

import com.example.spring.domain.Docente;
import com.example.spring.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @PostMapping
    public Docente criarDocente(@RequestBody Docente docente) {
        return docenteService.novoDocente(docente);
    }

    @GetMapping
    public List<Docente> listarDocentes() {
        return docenteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Docente getDocente(@PathVariable Long id) {
        return docenteService.procurarPorId(id);
    }
}
