package com.example.spring.repository;

import com.example.spring.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    List<Exercicio> findByUnidadeCurricularId(Long ucId);

}
