package com.example.spring.controller;

import com.example.spring.domain.user.User;
import com.example.spring.dto.CreateUserRequest;
import com.example.spring.dto.UserResponseDTO;
import com.example.spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ---------- POST ----------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody CreateUserRequest request) {

        service.criarUser(
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // ---------- GET ALL ----------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDTO> listar() {

        List<User> users = service.listarUsers();
        List<UserResponseDTO> resposta = new ArrayList<>();

        for (User u : users) {
            resposta.add(
                    new UserResponseDTO(
                            u.getId(),
                            u.getEmail(),
                            u.getRole()
                    )
            );
        }

        return resposta;
    }

    // ---------- GET BY ID ----------
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Long id) {

        User u = service.loadUser(id);

        if (u == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User não encontrado");
        }

        return ResponseEntity.ok(
                new UserResponseDTO(
                        u.getId(),
                        u.getEmail(),
                        u.getRole()
                )
        );
    }

    // ---------- DELETE ----------
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable Long id) {

        boolean apagou = service.apagarUser(id);

        if (!apagou) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User não encontrado");
        }

        return ResponseEntity.noContent().build(); // 204
    }

    // ---------- PUT ----------
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody CreateUserRequest request) {

        try {
            User atualizado = service.atualizarUser(
                    id,
                    request.getEmail(),
                    request.getRole()
            );

            if (atualizado == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User não encontrado");
            }

            return ResponseEntity.ok(
                    new UserResponseDTO(
                            atualizado.getId(),
                            atualizado.getEmail(),
                            atualizado.getRole()
                    )
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }


}
