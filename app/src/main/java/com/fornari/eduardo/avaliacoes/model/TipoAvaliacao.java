package com.fornari.eduardo.avaliacoes.model;

/**
 * Created by Eduardo Segura Fornari on 10/09/2016.
 */
public class TipoAvaliacao {

    public static final String TIPO_AVALIACAO = "TIPO_AVALIACAO";
    public static final String TIPO_AVALIACAO__ID = "TIPO_AVALIACAO__ID";
    public static final String TIPO_AVALIACAO_NOME = "TIPO_AVALIACAO_NOME";
    public static final String TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO = "TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO";
    public static final String TIPO_AVALIACAO_NOTIFICAR = "TIPO_AVALIACAO_NOTIFICAR";

    private int id;
    private String nome;
    private int antecedenciaNotificacao;
    private boolean notificar;

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

    public int getAntecedenciaNotificacao() {
        return antecedenciaNotificacao;
    }

    public void setAntecedenciaNotificacao(int antecedenciaNotificacao) {
        this.antecedenciaNotificacao = antecedenciaNotificacao;
    }

    public boolean isNotificar() {
        return notificar;
    }

    public void setNotificar(boolean notificar) {
        this.notificar = notificar;
    }
}