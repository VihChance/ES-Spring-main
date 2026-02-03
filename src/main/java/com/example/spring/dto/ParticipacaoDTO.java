package com.example.spring.dto;

import java.util.List;

public record ParticipacaoDTO(
        Long id,
        Long exercicioId,
        String exercicioTitulo,
        List<Long> fasesCompletas,
        boolean chamado
) {}

