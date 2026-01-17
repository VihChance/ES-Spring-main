package com.example.spring.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
//    private String email;

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<UnidadeCurricular> unidadesCurriculares;

    public Docente() {
    }

    public Docente(String nome, String email) {
        this.nome = nome;
//        this.email = email;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }

    public List<UnidadeCurricular> getUnidadesCurriculares() { return unidadesCurriculares; }
    public void setUnidadesCurriculares(List<UnidadeCurricular> unidadesCurriculares) { this.unidadesCurriculares = unidadesCurriculares; }
}
