package com.fornari.eduardo.avaliacoes.model;

/**
 * Created by Eduardo Segura Fornari on 10/09/2016.
 */
public class TipoAvaliacao {

    public static final String TIPO_AVALIACAO = "TIPO_AVALIACAO";
    public static final String TIPO_AVALIACAO__ID = "TIPO_AVALIACAO__ID";
    public static final String TIPO_AVALIACAO_NOME = "TIPO_AVALIACAO_NOME";
    public static final String TIPO_AVALIACAO_NOTIFICAR = "TIPO_AVALIACAO_NOTIFICAR";
    public static final String TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO = "TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO";
    public static final String TIPO_AVALIACAO_DESCRICAO = "TIPO_AVALIACAO_DESCRICAO";

    private int id;
    private String nome;
    private int antecedenciaNotificacao;
    private boolean notificar;
    private String descricao;

    //Usado apenas para criar a opção "Selecionar" para os dropdowns
    public TipoAvaliacao(String nome) {
        this.nome = nome;
    }

    public TipoAvaliacao(String nome, boolean notificar, int antecedenciaNotificacao, String descricao) {
        this.nome = nome;
        this.notificar = notificar;
        this.antecedenciaNotificacao = antecedenciaNotificacao;
        this.descricao = descricao;
    }

    public TipoAvaliacao(int id, String nome, boolean notificar, int antecedenciaNotificacao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.notificar = notificar;
        this.antecedenciaNotificacao = antecedenciaNotificacao;
        this.descricao = descricao;
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

    public String toString() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TipoAvaliacao.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        TipoAvaliacao other = (TipoAvaliacao) obj;
        if (!this.nome.equals(other.getNome()) || this.notificar!=other.isNotificar() || antecedenciaNotificacao!=other.getAntecedenciaNotificacao() || !this.descricao.equals(other.getDescricao())) {
            return false;
        }
        return true;
    }
}