package com.example.spring.services;

import com.example.spring.domain.Docente;
import com.example.spring.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public Docente novoDocente(Docente docente) {
        docente.setId(null); // garante criação e não update
        return docenteRepository.save(docente);
    }

    public List<Docente> listarTodos() {
        return docenteRepository.findAll();
    }

    public Docente procurarPorId(Long id) {
        return docenteRepository.findById(id).orElse(null);
    }
}
