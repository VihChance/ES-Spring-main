package com.example.spring.dto;

import com.example.spring.domain.Participacao;

public class ParticipacaoMapper {
    public static ParticipacaoDTO toDTO(Participacao p) {
        return new ParticipacaoDTO(
                p.getId(),
                p.getExercicio().getId(),
                p.getExercicio().getTitulo()
        );
    }
}
