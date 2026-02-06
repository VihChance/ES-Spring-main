package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.dto.ExercicioDTO;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.UnidadeCurricularRepository;
import com.example.spring.services.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.spring.dto.ExercicioCriadoMessage;


@Service
public class ExercicioService {

    @Autowired
    private ExercicioRepository exercicioRepository;

    @Autowired
    private UnidadeCurricularRepository ucRepository;

    @Autowired(required = false)
    private SimpMessagingTemplate messagingTemplate;


    /* ───────────── CRUD genérico ───────────── */
    public List<Exercicio> listarExercicios() {
        return exercicioRepository.findAll();
    }

    public Optional<Exercicio> encontrarPorId(Long id) {
        return exercicioRepository.findById(id);
    }

    public Exercicio guardar(Exercicio exercicio) {
        return exercicioRepository.save(exercicio);
    }

    public void apagar(Long id) {
        exercicioRepository.deleteById(id);
    }

    /* ─────────────────── NOVOS MÉTODOS ───────────────────── */

//    // 1. Criar exercício a partir de uma UC
//    public Exercicio criarExercicio(Long ucId, String titulo) {
//        UnidadeCurricular uc = ucRepository.findById(ucId)
//                .orElseThrow(() -> new RuntimeException("UC não encontrada"));
//
//        Exercicio ex = new Exercicio();
//        ex.setTitulo(titulo);
//        ex.setDataCriacao(LocalDate.now());
//        ex.setUnidadeCurricular(uc);
//
//        return exercicioRepository.save(ex);
//    }

    // 1. Criar exercício a partir de uma UC
    public Exercicio criarExercicio(Long ucId, String titulo) {
        UnidadeCurricular uc = ucRepository.findById(ucId)
                .orElseThrow(() -> new RuntimeException("UC não encontrada"));

        Exercicio ex = new Exercicio();
        ex.setTitulo(titulo);
        ex.setDataCriacao(LocalDate.now());
        ex.setUnidadeCurricular(uc);

        Exercicio salvo = exercicioRepository.save(ex);

        if (messagingTemplate != null) {
            ExercicioCriadoMessage msg = new ExercicioCriadoMessage(
                    salvo.getId(),
                    uc.getId(),
                    salvo.getTitulo()
            );

            // tópico por UC: /topic/exercicios.uc.{id}
            messagingTemplate.convertAndSend(
                    "/topic/exercicios.uc." + uc.getId(),
                    msg
            );
        }

        return salvo;
    }

    // 2. Listar exercícios de uma UC
    public List<Exercicio> listarPorUnidadeCurricular(Long ucId) {
        return exercicioRepository.findByUnidadeCurricularId(ucId);
    }

    @Transactional
    public Exercicio encerrarExercicio(Long exercicioId) {

        Exercicio ex = exercicioRepository.findById(exercicioId)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        ex.setEncerrado(true);
        return exercicioRepository.save(ex);
    }

    // 3. Procurar por ID
    public Exercicio procurarPorId(Long id) {
        return exercicioRepository.findById(id).orElse(null);
    }
}

