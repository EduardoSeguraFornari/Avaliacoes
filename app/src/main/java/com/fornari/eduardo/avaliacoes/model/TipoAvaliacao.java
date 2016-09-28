package com.fornari.eduardo.avaliacoes.model;

/**
 * Created by dufor on 10/09/2016.
 */
public class TipoAvaliacao {

    public static final String TIPO_AVALIACAO = "TIPO_AVALIACAO";
    public static final String TIPO_AVALIACAO__ID = "TIPO_AVALIACAO__ID";
    public static final String TIPO_AVALIACAO_NOME = "TIPO_AVALIACAO_NOME";

    private int id;
    private String nome;
    public TipoAvaliacao(String nome){
        this.nome = nome;
    }

    public TipoAvaliacao(int id, String nome) {
        this.id = id;
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

    public String toString(){
        return nome;
    }
}
