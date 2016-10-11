package com.fornari.eduardo.avaliacoes.model;

import java.util.List;

/**
 * Created by Eduardo Segura Fornari on 10/09/2016.
 */
public class Disciplina implements Comparable<Disciplina> {
    public static final String DISCIPLINA = "DISCIPLINA";
    public static final String DISCIPLINA_ID = "DISCIPLINA_ID";
    public static final String DISCIPLINA_NOME = "DISCIPLINA_NOME";


    private int id;
    private String nome;
    private List<Avaliacao> avaliacoes;

    public Disciplina(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public String toString() {
        return nome;
    }

    @Override
    public int compareTo(Disciplina o) {
        return 0;
    }
}