package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.UnidadeCurricularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private UnidadeCurricularRepository ucRepository;

    /* ───────────── CRUD genérico que já tinhas ───────────── */
    public List<Exercicio> listarExercicios() { return exercicioRepository.findAll(); }

    public Optional<Exercicio> encontrarPorId(Long id) { return exercicioRepository.findById(id); }

    public Exercicio guardar(Exercicio exercicio) { return exercicioRepository.save(exercicio); }

    public void apagar(Long id) { exercicioRepository.deleteById(id); }

    /* ─────────────────── NOVOS MÉTODOS ───────────────────── */

    // 1. Criar exercício a partir de uma UC
    public Exercicio criarExercicio(Long ucId, String titulo) {
        Optional<UnidadeCurricular> ucOpt = ucRepository.findById(ucId);
        if (ucOpt.isEmpty()) return null;              // ou lança exceção

        Exercicio ex = new Exercicio();
        ex.setTitulo(titulo);
        ex.setDataCriacao(LocalDate.now());
        ex.setUnidadeCurricular(ucOpt.get());

        return exercicioRepository.save(ex);
    }

    // 2. Listar exercícios de uma UC
    public List<Exercicio> listarPorUnidadeCurricular(Long ucId) {
        return exercicioRepository.findByUnidadeCurricularId(ucId);
    }

    // 3. Procurar por ID (nome que o controller espera)
    public Exercicio procurarPorId(Long id) {
        return exercicioRepository.findById(id).orElse(null);
    }
}
