package com.example.spring.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Aluno aluno;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercicio_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Exercicio exercicio;

    private boolean terminado = false;

    private Double nota;

    private boolean chamado = false;

    @ElementCollection
    private List<Long> fasesConcluidas = new ArrayList<>();

    // ─────────────── CONSTRUTORES ───────────────
    public Participacao() {}

    public Participacao(Aluno aluno, Exercicio exercicio) {
        this.aluno = aluno;
        this.exercicio = exercicio;
    }

    // ─────────────── GETTERS/SETTERS ───────────────

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public boolean isChamado() {
        return chamado;
    }

    public void setChamado(boolean chamado) {
        this.chamado = chamado;
    }

    public List<Long> getFasesConcluidas() {
        return fasesConcluidas;
    }

    public void setFasesConcluidas(List<Long> fasesConcluidas) {
        this.fasesConcluidas = fasesConcluidas;
    }
}
