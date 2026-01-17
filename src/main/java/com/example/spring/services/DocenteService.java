package com.example.spring.services;

import com.example.spring.domain.Docente;
import com.example.spring.repository.DocenteRepository;
import com.example.spring.services.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DocenteService {

//    private final DocenteRepository repository;
//
//    public DocenteService(DocenteRepository repository) {
//        this.repository = repository;
//    }
//
//    public Docente procurarPorEmail(String email) {
//        return repository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException("Docente com email '" + email + "' não encontrado."));
//    }
}
