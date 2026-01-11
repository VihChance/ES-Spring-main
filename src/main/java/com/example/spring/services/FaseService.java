package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Fase;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.FaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaseService {

    @Autowired
    private FaseRepository repo;

    @Autowired
    private ExercicioRepository exercicioRepo;

    /** Cria uma nova fase dentro de um exercício */
    public Fase criar(Long exercicioId, String titulo, int ordem) {
        Exercicio ex = exercicioRepo.findById(exercicioId)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        Fase f = new Fase();
        f.setTitulo(titulo);
        f.setOrdem(ordem);
        f.setExercicio(ex);       // liga a fase ao exercício

        return repo.save(f);      // guarda e devolve a fase
    }

    public List<Fase> listarPorExercicio(Long exercicioId){
        return repo.findByExercicioIdOrderByOrdemAsc(exercicioId);
    }

}
