package com.example.spring.repository;

import com.example.spring.domain.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
}
