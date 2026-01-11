package com.example.spring.repository;

import com.example.spring.domain.UnidadeCurricular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {

    List<UnidadeCurricular> findByDocenteId(Long docenteId);
}
