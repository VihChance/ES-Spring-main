package com.example.spring.controller;

import com.example.spring.domain.Participacao;
import com.example.spring.dto.NovaParticipacaoDTO;
import com.example.spring.dto.ParticipacaoDTO;
import com.example.spring.dto.ParticipacaoMapper;
import com.example.spring.repository.ParticipacaoRepository;
import com.example.spring.services.ParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/participacoes")
@CrossOrigin(origins = "*")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService participacaoService;

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    // ─────────── Criar participação ───────────
    @PreAuthorize("hasRole('ALUNO')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ParticipacaoDTO> criarViaJson(@RequestBody NovaParticipacaoDTO dto) {
        try {
            Participacao p =
                    participacaoService.criarParticipacao(dto.alunoId(), dto.exercicioId());

            return ResponseEntity.ok(ParticipacaoMapper.toDTO(p));

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    // ─────────── Marcar fase como concluída ───────────
    @PreAuthorize("hasRole('ALUNO')")
    @PostMapping("/{participacaoId}/fase/{faseId}/concluir")
    public ResponseEntity<ParticipacaoDTO> concluirFase(
            @PathVariable Long participacaoId,
            @PathVariable Long faseId) {

        ParticipacaoDTO dto =
                participacaoService.marcarFaseConcluida(participacaoId, faseId);

        return ResponseEntity.ok(dto);
    }


    // ─────────── Chamar docente ───────────
    @PreAuthorize("hasRole('ALUNO')")
    @PutMapping("/{participacaoId}/chamar-docente")
    public ResponseEntity<ParticipacaoDTO> chamarDocente(
            @PathVariable Long participacaoId) {

        ParticipacaoDTO dto =
                participacaoService.chamarDocente(participacaoId);

        return ResponseEntity.ok(dto);
    }

    // ─────────── Atribuir nota ───────────
    @PreAuthorize("hasRole('DOCENTE')")
    @PutMapping("/{participacaoId}/atribuir-nota")
    public ResponseEntity<ParticipacaoDTO> atribuirNota(
            @PathVariable Long participacaoId,
            @RequestParam Double nota) {

        ParticipacaoDTO dto =
                participacaoService.atribuirNota(participacaoId, nota);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{participacaoId}/terminar")
    public ResponseEntity<ParticipacaoDTO> terminar(
            @PathVariable Long participacaoId) {
        return ResponseEntity.ok(
                participacaoService.terminarParticipacao(participacaoId)
        );
    }


    // ─────────── Listar participações do aluno ───────────
    @PreAuthorize("hasRole('ALUNO')")
    @GetMapping("/aluno/{alunoId}")
    public List<ParticipacaoDTO> listarPorAluno(@PathVariable Long alunoId) {

        return participacaoRepository
                .findAllByAlunoIdFetchExercicio(alunoId)
                .stream()
                .map(ParticipacaoMapper::toDTO)
                .toList();
    }
}
