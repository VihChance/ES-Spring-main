package com.example.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CriarUCRequest {

    @NotBlank
    private final String nome;

    @NotNull
    private final Long docenteId;

    public CriarUCRequest(String nome, Long docenteId) {
        this.nome = nome;
        this.docenteId = docenteId;
    }

    public String getNome() { return nome; }
    public Long getDocenteId() { return docenteId; }
}
