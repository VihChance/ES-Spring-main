package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Exercicio;
import com.example.spring.domain.Participacao;
import com.example.spring.dto.ParticipacaoDTO;
import com.example.spring.dto.ParticipacaoMapper;
import com.example.spring.repository.AlunoRepository;
import com.example.spring.repository.ExercicioRepository;
import com.example.spring.repository.ParticipacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Participacao criarParticipacao(Long alunoId, Long exercicioId) {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Exercicio exercicio = exercicioRepository.findById(exercicioId)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        Optional<Participacao> existente =
                participacaoRepository.findByAlunoAndExercicio(aluno, exercicio);

        if (existente.isPresent()) {
            Participacao p = existente.get();
            p.getExercicio().getId(); // força load
            return p;
        }

        Participacao nova = new Participacao(aluno, exercicio);
        Participacao salva = participacaoRepository.save(nova);
        salva.getExercicio().getId();
        return salva;
    }

    // ─────────── Marcar fase como concluída ───────────
    @Transactional
    public ParticipacaoDTO marcarFaseConcluida(Long participacaoId, Long faseId) {

        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        if (!p.getFasesConcluidas().contains(faseId)) {
            p.getFasesConcluidas().add(faseId);
        }

        participacaoRepository.save(p);

        return ParticipacaoMapper.toDTO(p);
    }


    // ─────────────── Chamar docente ──────────────
    @Transactional
    public ParticipacaoDTO chamarDocente(Long participacaoId) {

        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setChamado(true);
        participacaoRepository.save(p);

        return ParticipacaoMapper.toDTO(p);
    }

    // ─────────────── Atribuir nota ───────────────
    @Transactional
    public ParticipacaoDTO atribuirNota(Long participacaoId, Double nota) {

        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        p.setNota(nota);
        p.setTerminado(true);
        participacaoRepository.save(p);

        return ParticipacaoMapper.toDTO(p);
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
