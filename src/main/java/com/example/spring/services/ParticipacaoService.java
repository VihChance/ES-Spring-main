package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Participacao;
import com.example.spring.repository.AlunoRepository;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.ParticipacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipacaoService {

    private final ParticipacaoRepository participacaoRepository;
    private final AlunoRepository alunoRepository;
    private final ExercicioRepository exercicioRepository;

    public ParticipacaoService(
            ParticipacaoRepository participacaoRepository,
            AlunoRepository alunoRepository,
            ExercicioRepository exercicioRepository
    ) {
        this.participacaoRepository = participacaoRepository;
        this.alunoRepository = alunoRepository;
        this.exercicioRepository = exercicioRepository;
    }

    // ───────────── Criar participação ─────────────
    public Participacao criarParticipacao(Long alunoId, Long exercicioId) {

        if (participacaoRepository.existsByAlunoIdAndExercicioId(alunoId, exercicioId)) {
            throw new RuntimeException("Este aluno já participou neste exercício.");
        }

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Exercicio exercicio = exercicioRepository.findById(exercicioId)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        Participacao participacao = new Participacao(aluno, exercicio);
        participacao.setFasesCompletas(new ArrayList<>());

        return participacaoRepository.save(participacao);
    }

    // ─────────── Marcar fase concluída ───────────
    public boolean marcarFaseConcluida(Long participacaoId, Long faseId) {

        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        // Proteção contra fase inválida
        boolean faseValida = p.getExercicio().getFases()
                .stream()
                .anyMatch(f -> f.getId().equals(faseId));

        if (!faseValida) {
            throw new RuntimeException("Esta fase não pertence a este exercício.");
        }

        if (p.getFasesCompletas() == null) {
            p.setFasesCompletas(new ArrayList<>());
        }

        if (!p.getFasesCompletas().contains(faseId)) {
            p.getFasesCompletas().add(faseId);
            participacaoRepository.save(p);
        }

        return true;
    }

    // ─────────────── Chamar docente ──────────────
    public boolean chamarDocente(Long participacaoId) {
        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setChamado(true);
        participacaoRepository.save(p);
        return true;
    }

    // ─────────────── Atribuir nota ───────────────
    public boolean atribuirNota(Long participacaoId, Double nota) {
        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setNota(nota);
        p.setTerminado(true);
        participacaoRepository.save(p);
        return true;
    }

    // ───────────── Listar por aluno ──────────────
    public List<Participacao> listarPorAluno(Long alunoId) {
        return participacaoRepository.findByAlunoId(alunoId);
    }

    // ───────────── Listar todas ──────────────
    public List<Participacao> listarParticipacoes() {
        return participacaoRepository.findAll();
    }
}
