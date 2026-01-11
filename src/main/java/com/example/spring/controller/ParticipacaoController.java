package com.example.spring.controller;

import com.example.spring.domain.Participacao;
import com.example.spring.services.ParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.spring.dto.NovaParticipacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/participacoes")
@CrossOrigin(origins = "*")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService participacaoService;

    /* ---------- JSON no corpo ---------- */
    @PostMapping(consumes = "application/json")
    public Participacao criarViaJson(@RequestBody NovaParticipacaoDTO dto) {
        try {
            return participacaoService.criarParticipacao(dto.alunoId(), dto.exercicioId());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    e.getMessage()
            );
        }
    }


//    /* ---------- (Opcional) Query-params ---------- */
//    @PostMapping(params = { "alunoId", "exercicioId" })
//    public Participacao criarViaQuery(@RequestParam Long alunoId,
//                                      @RequestParam Long exercicioId) {
//        return participacaoService.criarParticipacao(alunoId, exercicioId);
//    }

    /* --------- RESTO DOS ENDPOINTS --------- */
    @PostMapping("/{participacaoId}/fase/{faseId}/concluir")
    public boolean concluirFase(@PathVariable Long participacaoId,
                                @PathVariable Long faseId) {
        return participacaoService.marcarFaseConcluida(participacaoId, faseId);
    }

    @PostMapping("/{participacaoId}/chamar-docente")
    public boolean chamarDocente(@PathVariable Long participacaoId) {
        return participacaoService.chamarDocente(participacaoId);
    }

    @PostMapping("/{participacaoId}/atribuir-nota")
    public boolean atribuirNota(@PathVariable Long participacaoId,
                                @RequestParam Double nota) {
        return participacaoService.atribuirNota(participacaoId, nota);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<Participacao> listarParticipacoesPorAluno(@PathVariable Long alunoId) {
        return participacaoService.listarPorAluno(alunoId);
    }
}