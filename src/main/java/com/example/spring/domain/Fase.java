package com.example.spring.domain;

import jakarta.persistence.*;

@Entity
public class Fase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private int ordem;        // 1-N

    @ManyToOne
    @JoinColumn(name = "exercicio_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Exercicio exercicio;

    /* getters / setters */
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }

    public Exercicio getExercicio() { return exercicio; }
    public void setExercicio(Exercicio exercicio) { this.exercicio = exercicio; }
}
