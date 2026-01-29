package com.example.spring.controller;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Docente;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.domain.user.User;
import com.example.spring.domain.user.UserRole;
import com.example.spring.dto.CriarAlunoRequest;
import com.example.spring.dto.CriarDocenteRequest;
import com.example.spring.dto.CriarUCRequest;
import com.example.spring.repository.AlunoRepository;
import com.example.spring.repository.DocenteRepository;
import com.example.spring.services.UnidadeCurricularService;
import com.example.spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserService userService;
    private final AlunoRepository alunoRepo;
    private final DocenteRepository docenteRepo;
    private final UnidadeCurricularService ucService;

    public AdminController(
            UserService userService,
            AlunoRepository alunoRepo,
            DocenteRepository docenteRepo,
            UnidadeCurricularService ucService
    ) {
        this.userService = userService;
        this.alunoRepo = alunoRepo;
        this.docenteRepo = docenteRepo;
        this.ucService = ucService;
    }

    // ---------- CRIAR ALUNO ----------
    @PostMapping("/criar-aluno")
    public User criarAluno(@RequestBody @Valid CriarAlunoRequest request) {

        Aluno aluno = new Aluno(request.getNome(), request.getEmail());
        alunoRepo.save(aluno);

        return userService.criarUserComVinculo(
                request.getEmail(),
                request.getPassword(),
                UserRole.ALUNO,
                aluno,
                null
        );
    }

    // ---------- CRIAR DOCENTE ----------
    @PostMapping("/criar-docente")
    public User criarDocente(@RequestBody @Valid CriarDocenteRequest request) {

        Docente docente = new Docente(request.getNome(), request.getEmail());
        docenteRepo.save(docente);

        return userService.criarUserComVinculo(
                request.getEmail(),
                request.getPassword(),
                UserRole.DOCENTE,
                null,
                docente
        );
    }

    // ---------- CRIAR UC ----------
    @PostMapping("/criar-uc")
    public UnidadeCurricular criarUC(@RequestBody @Valid CriarUCRequest request) {

        Docente docente = docenteRepo.findById(request.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente não encontrado"));

        return ucService.criarUnidadeCurricular(request.getNome(), docente);
    }

    // ---------- LISTAR USERS ----------
    @GetMapping("/users")
    public List<User> listarTodosUsers() {
        return userService.listarUsers();
    }

    // ---------- APAGAR USER ----------
    @DeleteMapping("/users/{id}")
    public boolean apagarUser(@PathVariable Long id) {
        return userService.apagarUser(id);
    }
}
