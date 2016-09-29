package com.fornari.eduardo.avaliacoes.model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Eduardo Segura Fornari on 10/09/2016.
 */
public class Avaliacao {

    public static final String AVALIACAO = "AVALIACAO";
    public static final String AVALIACAO_ID = "AVALIACAO_ID";
    public static final String AVALIACAO_TIPO_AVALIACAO__ID = "AVALIACAO_TIPO_AVALIACAO__ID";
    public static final String AVALIACAO_DATA = "AVALIACAO_DATA";
    public static final String AVALIACAO_OBSERVACAO = "AVALIACAO_OBSERVACAO";
    public static final String AVALIACAO_DISCIPLINA__ID = "AVALIACAO_DISCIPLINA__ID";

    private int id;
    private TipoAvaliacao tipoAvaliacao;
    private Date data;
    private String observacao;
    private Disciplina disciplina;

    //foreign keys
    private int tipoAvaliacaoId;
    private int disciplinaId;

    public Avaliacao(int tipoAvaliacaoId, Date data, String observacao, int disciplinaId) {
        this.tipoAvaliacaoId = tipoAvaliacaoId;
        this.data = data;
        this.observacao = observacao;
        this.disciplinaId = disciplinaId;
    }

    public Avaliacao(int id, TipoAvaliacao tipoAvaliacao, Date data, String observacao,Disciplina disciplina) {
        this.id = id;
        this.tipoAvaliacao = tipoAvaliacao;
        this.data = data;
        this.observacao = observacao;
        this.disciplina = disciplina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public int getTipoAvaliacaoId() {
        return tipoAvaliacaoId;
    }

    public void setTipoAvaliacaoId(int tipoAvaliacaoId) {
        this.tipoAvaliacaoId = tipoAvaliacaoId;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String toString(){
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        String dt = format.format(data);
        return this.tipoAvaliacao.getNome() + " - " + dt;
    }
}