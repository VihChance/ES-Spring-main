package com.example.spring.services;

import com.example.spring.domain.*;
import com.example.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipacaoService {

    @Autowired private ParticipacaoRepository participacaoRepository;
    @Autowired private AlunoRepository        alunoRepository;
    @Autowired private ExercicioRepository    exercicioRepository;

    /* ───────────── Criar participação ───────────── */
    public Participacao criarParticipacao(Long alunoId, Long exercicioId){

        // 1) evita duplicados
        if (participacaoRepository.existsByAlunoIdAndExercicioId(alunoId, exercicioId)) {
            throw new RuntimeException("Este aluno já entrou neste exercício (participação já existe).");
            // depois podemos trocar por um erro 409 mais bonito
        }

        // 2) valida aluno e exercício
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Exercicio exercicio = exercicioRepository.findById(exercicioId)
                .orElseThrow(() -> new RuntimeException("Exercício não encontrado"));

        // 3) cria e inicializa lista vazia (evita NullPointer depois)
        Participacao p = new Participacao(aluno, exercicio);
        p.setFasesCompletas(new ArrayList<>());   // <- importante

        return participacaoRepository.save(p);
    }


    /* ─────────── Marcar fase concluída ─────────── */
    public boolean marcarFaseConcluida(Long participacaoId, Long faseId){

        Participacao p = participacaoRepository.findById(participacaoId)
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));

        // a fase pertence ao exercício desta participação?
        boolean faseValida = p.getExercicio()
                .getFases()
                .stream()
                .anyMatch(f -> f.getId().equals(faseId));

        if (!faseValida) {
            throw new RuntimeException("Erro: esta fase não pertence ao exercício da participação.");
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


    /* ─────────────── Chamar docente ────────────── */
    public boolean chamarDocente(Long participacaoId){
        Participacao p = participacaoRepository.findById(participacaoId).orElseThrow(
                () -> new RuntimeException("Participação não encontrada"));

        p.setChamado(true);
        participacaoRepository.save(p);
        return true;
    }

    /* ─────────────── Atribuir nota ─────────────── */
    public boolean atribuirNota(Long participacaoId, Double nota){
        Participacao p = participacaoRepository.findById(participacaoId).orElseThrow(
                () -> new RuntimeException("Participação não encontrada"));

        p.setNota(nota);
        p.setTerminado(true);
        participacaoRepository.save(p);
        return true;
    }

    /* ───────────── Listar por aluno ────────────── */
    public List<Participacao> listarPorAluno(Long alunoId){
        return participacaoRepository.findByAlunoId(alunoId);
    }

    /* ───────────── Listar todas ────────────── */
    public List<Participacao> listarParticipacoes(){
        return participacaoRepository.findAll();
    }
}
