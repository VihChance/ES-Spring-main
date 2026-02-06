package com.example.spring.services;

import com.example.spring.domain.Exercicio;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.UnidadeCurricularRepository;
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

    @Autowired
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

        //  Notificar todos os clientes que um exercício foi criado
        ExercicioCriadoMessage msg = new ExercicioCriadoMessage(
                salvo.getId(),
                uc.getId(),
                salvo.getTitulo()
        );

        // tópico geral ou por-UC; aqui faço por-UC para ser mais “pro”
        messagingTemplate.convertAndSend(
                "/topic/exercicios.uc." + uc.getId(),
                msg
        );

        return salvo;
    }

    // 2. Listar exercícios de uma UC
    public List<Exercicio> listarPorUnidadeCurricular(Long ucId) {
        return exercicioRepository.findByUnidadeCurricularId(ucId);
    }

    // 3. Procurar por ID
    public Exercicio procurarPorId(Long id) {
        return exercicioRepository.findById(id).orElse(null);
    }
}

