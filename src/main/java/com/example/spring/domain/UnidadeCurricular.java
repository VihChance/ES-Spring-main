package com.example.spring.domain;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UnidadeCurricular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

//    @OneToMany(mappedBy = "unidadeCurricular", cascade = CascadeType.ALL)
//    private List<Exercicio> exercicios;

    @OneToMany(mappedBy = "unidadeCurricular", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Exercicio> exercicios;

    public UnidadeCurricular() {
    }

    public UnidadeCurricular(String nome, Docente docente) {
        this.nome = nome;
        this.docente = docente;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }

    public List<Exercicio> getExercicios() { return exercicios; }
    public void setExercicios(List<Exercicio> exercicios) { this.exercicios = exercicios; }
}
