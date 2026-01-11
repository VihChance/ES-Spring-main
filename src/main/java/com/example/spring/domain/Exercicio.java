package com.example.spring.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private LocalDate dataCriacao;


    @ManyToOne
    @JoinColumn(name = "uc_id")
    private UnidadeCurricular unidadeCurricular;

    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fase> fases;

//    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL)
//    private List<Participacao> participacoes;
    @OneToMany(mappedBy = "exercicio", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Participacao> participacoes;


    public Exercicio() {
    }

    public Exercicio(String titulo, LocalDate dataCriacao, UnidadeCurricular unidadeCurricular) {
        this.titulo = titulo;
        this.dataCriacao = dataCriacao;
        this.unidadeCurricular = unidadeCurricular;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public UnidadeCurricular getUnidadeCurricular() { return unidadeCurricular; }
    public void setUnidadeCurricular(UnidadeCurricular unidadeCurricular) { this.unidadeCurricular = unidadeCurricular; }

    public List<Fase> getFases() { return fases; }
    public void setFases(List<Fase> fases) { this.fases = fases; }

    public List<Participacao> getParticipacoes() { return participacoes; }
    public void setParticipacoes(List<Participacao> participacoes) { this.participacoes = participacoes; }
}
