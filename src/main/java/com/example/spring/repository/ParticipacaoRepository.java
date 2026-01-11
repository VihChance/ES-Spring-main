package com.example.spring.repository;

import com.example.spring.domain.Participacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Long> {
    boolean existsByAlunoIdAndExercicioId(Long alunoId, Long exercicioId);

    List<Participacao> findByAlunoId(Long alunoId);
    List<Participacao> findByExercicioId(Long exercicioId);
}
