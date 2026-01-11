package com.example.spring.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Aluno aluno;

    // usar rest para controlar loop
    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Exercicio exercicio;


    private boolean terminado;
    private Double nota;
    private boolean chamado;

    @ElementCollection
    private List<Long> fasesCompletas; // IDs das fases que o aluno concluiu

    public Participacao() {
    }

    public Participacao(Aluno aluno, Exercicio exercicio) {
        this.aluno = aluno;
        this.exercicio = exercicio;
        this.terminado = false;
        this.chamado = false;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Exercicio getExercicio() { return exercicio; }
    public void setExercicio(Exercicio exercicio) { this.exercicio = exercicio; }

    public boolean isTerminado() { return terminado; }
    public void setTerminado(boolean terminado) { this.terminado = terminado; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public boolean isChamado() { return chamado; }
    public void setChamado(boolean chamado) { this.chamado = chamado; }

    public List<Long> getFasesCompletas() { return fasesCompletas; }
    public void setFasesCompletas(List<Long> fasesCompletas) { this.fasesCompletas = fasesCompletas; }
}
