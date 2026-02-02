package com.example.spring.repository;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Participacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipacaoRepository extends JpaRepository<Participacao, Long> {
//    boolean existsByAlunoIdAndExercicioId(Long alunoId, Long exercicioId);

    List<Participacao> findByAlunoId(Long alunoId);
    List<Participacao> findByExercicioId(Long exercicioId);

    Optional<Participacao> findByAlunoAndExercicio(Aluno aluno, Exercicio exercicio);

    @Query("""
       select p from Participacao p
       join fetch p.exercicio e
       where p.aluno.id = :alunoId
       """)
    List<Participacao> findAllByAlunoIdFetchExercicio(@Param("alunoId") Long alunoId);
}
