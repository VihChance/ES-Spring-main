package com.example.spring.dto;

import com.example.spring.domain.Participacao;
import java.util.List;

public class ParticipacaoMapper {

    public static ParticipacaoDTO toDTO(Participacao p) {

        List<Long> fases = (p.getFasesConcluidas() == null)
                ? List.of()
                : p.getFasesConcluidas();

        return new ParticipacaoDTO(
                p.getId(),
                p.getExercicio().getId(),
                p.getExercicio().getTitulo(),
                fases,
                p.isChamado(),
                p.isTerminado(),
                p.getNota()
        );
    }
}
