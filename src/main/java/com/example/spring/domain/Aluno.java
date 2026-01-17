package com.example.spring.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
//    private String email;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Participacao> participacoes;


    public Aluno() {
    }

    public Aluno(String nome, String email) {
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

    public List<Participacao> getParticipacoes() { return participacoes; }
    public void setParticipacoes(List<Participacao> participacoes) { this.participacoes = participacoes; }
}
