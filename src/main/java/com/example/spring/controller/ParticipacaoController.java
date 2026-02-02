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

    // Apenas ALUNO pode entrar num exercício
    @PreAuthorize("hasRole('ALUNO')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ParticipacaoDTO> criarViaJson(@RequestBody NovaParticipacaoDTO dto) {
        try {
            Participacao p = participacaoService.criarParticipacao(dto.alunoId(), dto.exercicioId());
            // garante que o Exercicio está carregado
            p.getExercicio().getId();
            return ResponseEntity.ok(ParticipacaoMapper.toDTO(p));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    // ALUNO marca fase como concluída
    @PreAuthorize("hasRole('ALUNO')")
    @PostMapping("/{participacaoId}/fase/{faseId}/concluir")
    public boolean concluirFase(@PathVariable Long participacaoId,
                                @PathVariable Long faseId) {
        return participacaoService.marcarFaseConcluida(participacaoId, faseId);
    }

    // ALUNO chama o docente
    @PreAuthorize("hasRole('ALUNO')")
    @PostMapping("/{participacaoId}/chamar-docente")
    public boolean chamarDocente(@PathVariable Long participacaoId) {
        return participacaoService.chamarDocente(participacaoId);
    }

    // DOCENTE atribui nota e termina
    @PreAuthorize("hasRole('DOCENTE')")
    @PostMapping("/{participacaoId}/atribuir-nota")
    public boolean atribuirNota(@PathVariable Long participacaoId,
                                @RequestParam Double nota) {
        return participacaoService.atribuirNota(participacaoId, nota);
    }

    // ALUNO vê suas participações — devolve DTO com exercicioId + titulo
    @PreAuthorize("hasRole('ALUNO')")
    @GetMapping("/aluno/{alunoId}")
    public List<ParticipacaoDTO> listarPorAluno(@PathVariable Long alunoId) {
        return participacaoRepository.findAllByAlunoIdFetchExercicio(alunoId)
                .stream()
                .map(ParticipacaoMapper::toDTO)
                .toList();
    }
}
