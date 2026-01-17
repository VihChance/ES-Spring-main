package com.example.spring.controller;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.user.User;
import com.example.spring.services.UserService;
import com.example.spring.services.AlunoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService alunoService;
    private final UserService userService;

    public AlunoController(AlunoService alunoService, UserService userService) {
        this.alunoService = alunoService;
        this.userService = userService;
    }

    // ✅ Criação de Aluno deve estar aberta (para fins de registo inicial)
    @PostMapping
    @PreAuthorize("permitAll()")
    public Aluno criar(@RequestBody Aluno aluno) {
        return alunoService.novoAluno(aluno);
    }

    // ✅ Novo: retornar o perfil do aluno logado
    @GetMapping("/me")
    @PreAuthorize("hasRole('ALUNO')")
    public Aluno meuPerfil(Authentication authentication) {
        String email = authentication.getName(); // ← vem do JWT
        User user = userService.findByEmail(email);
        return user.getAluno(); // ← vinculado ao User
    }

    // 🔐 Docente deveria ver esta lista (mas por enquanto deixamos aberta)
    @GetMapping
    @PreAuthorize("hasRole('DOCENTE')") // ou ADMIN futuramente
    public List<Aluno> listar() {
        return alunoService.listarTodos();
    }

    // 🔐 Obter aluno por ID (acesso restrito)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCENTE')")
    public Aluno obter(@PathVariable Long id) {
        return alunoService.procurarPorId(id);
    }
}
