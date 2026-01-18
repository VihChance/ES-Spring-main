package com.example.spring.services;

import com.example.spring.domain.Docente;
import com.example.spring.domain.UnidadeCurricular;
import com.example.spring.repository.UnidadeCurricularRepository;
import com.example.spring.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeCurricularService {

    @Autowired
    private UnidadeCurricularRepository ucRepository;

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
}
