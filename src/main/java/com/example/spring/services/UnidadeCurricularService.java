package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.domain.Docente;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.repository.AlunoRepository;
import com.example.spring.repository.UnidadeCurricularRepository;
import com.example.spring.services.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeCurricularService {

    private final UnidadeCurricularRepository ucRepository;
    private final AlunoRepository alunoRepository;

    public UnidadeCurricularService(UnidadeCurricularRepository ucRepository,
                                    AlunoRepository alunoRepository) {
        this.ucRepository = ucRepository;
        this.alunoRepository = alunoRepository;
    }

    /* ─────────── Criar UC ─────────── */
    public UnidadeCurricular criarUnidadeCurricular(String nome, Docente docente) {
        UnidadeCurricular uc = new UnidadeCurricular(nome, docente);
        return ucRepository.save(uc);
    }

    /* ─────────── Listar todas ─────── */
    public List<UnidadeCurricular> listarTodas() {
        return ucRepository.findAll();
    }

    /* ───── Listar UCs por docente ─── */
    public List<UnidadeCurricular> listarPorDocente(Long docenteId) {
        return ucRepository.findByDocenteId(docenteId);
    }

    public UnidadeCurricular procurarPorId(Long id) {
        return ucRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UC não encontrada"));
    }

    /* ───── Listar UCs de um aluno ─── */
    public List<UnidadeCurricular> listarPorAluno(Long alunoId) {
        return ucRepository.findByAlunosId(alunoId);
    }

    /* ───── Associar aluno a uma UC ─── */
    @Transactional
    public void associarAlunoAUC(Long alunoId, Long ucId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        UnidadeCurricular uc = ucRepository.findById(ucId)
                .orElseThrow(() -> new NotFoundException("UC não encontrada"));

        if (!aluno.getUnidadesCurriculares().contains(uc)) {
            aluno.getUnidadesCurriculares().add(uc);
        }

        alunoRepository.save(aluno); // lado “dono” da relação
    }
}
