package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Fase;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.FaseRepository;
import com.example.spring.services.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaseService {

    private final FaseRepository repo;
    private final ExercicioRepository exercicioRepo;

    public FaseService(FaseRepository repo, ExercicioRepository exercicioRepo) {
        this.repo = repo;
        this.exercicioRepo = exercicioRepo;
    }

    /** Cria uma nova fase dentro de um exercício */
    public Fase criar(Long exercicioId, String titulo, int ordem) {
        Exercicio ex = exercicioRepo.findById(exercicioId)
                .orElseThrow(() -> new NotFoundException("Exercício não encontrado"));

        Fase f = new Fase();
        f.setTitulo(titulo);
        f.setOrdem(ordem);
        f.setExercicio(ex);   // liga a fase ao exercício

        return repo.save(f);
    }

    /** Lista as fases de um exercício, ordenadas pela ordem */
    public List<Fase> listarPorExercicio(Long exercicioId) {
        return repo.findByExercicioIdOrderByOrdemAsc(exercicioId);
    }
}
