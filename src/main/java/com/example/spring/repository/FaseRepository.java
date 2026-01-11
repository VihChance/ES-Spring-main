package com.example.spring.repository;

import com.example.spring.domain.Fase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FaseRepository extends JpaRepository<Fase, Long> {
    List<Fase> findByExercicioIdOrderByOrdemAsc(Long exercicioId);
}
