package com.example.spring.services;

import com.example.spring.domain.Aluno;
import com.example.spring.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno novoAluno(Aluno aluno) {
        aluno.setId(null); // evita update por acidente
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno procurarPorId(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }
}
