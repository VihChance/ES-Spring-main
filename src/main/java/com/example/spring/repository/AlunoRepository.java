package com.example.spring.repository;

import com.example.spring.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Exemplo: Optional<Aluno> findByEmail(String email);
}
