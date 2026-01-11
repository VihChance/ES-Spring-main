package com.example.spring.controller;

import com.example.spring.domain.Aluno;
import com.example.spring.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ALUNO')")


public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    // criar aluno
    @PostMapping
    public Aluno criar(@RequestBody Aluno aluno) {
        return alunoService.novoAluno(aluno);
    }

    // listar todos
    @GetMapping
    public List<Aluno> listar() {
        return alunoService.listarTodos();
    }

    // obter por id
    @GetMapping("/{id}")
    public Aluno obter(@PathVariable Long id) {
        return alunoService.procurarPorId(id);
    }
}
