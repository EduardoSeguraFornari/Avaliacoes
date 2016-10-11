package com.fornari.eduardo.avaliacoes.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dufor on 10/10/2016.
 */
public class Notificacao {
    public static final String NOTIFICACAO = "NOTIFICACAO";
    public static final String NOTIFICACAO_ID = "NOTIFICACAO_ID";
    public static final String NOTIFICACAO_NOTIFICAR = "NOTIFICACAO_NOTIFICAR";
    public static final String NOTIFICACAO_SOM = "NOTIFICACAO_SOM";
    public static final String NOTIFICACAO_VIBRACAO = "NOTIFICACAO_VIBRACAO";
    public static final String NOTIFICACAO_HORARIO = "NOTIFICACAO_HORARIO";

    private int id;
    private boolean notificar;
    private boolean som;
    private boolean vibracao;
    private Date horario;

    public Notificacao() {
        this.notificar = true;
        this.som = true;
        this.vibracao = true;

        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1, 13, 30);
        this.horario = calendar.getTime();
    }

    public Notificacao(boolean notificar, boolean som, boolean vibracao, Date horario) {
        this.notificar = notificar;
        this.som = som;
        this.vibracao = vibracao;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNotificar() {
        return notificar;
    }

    public void setNotificar(boolean notificar) {
        this.notificar = notificar;
    }

    public boolean isSom() {
        return som;
    }

    public void setSom(boolean som) {
        this.som = som;
    }

    public boolean isVibracao() {
        return vibracao;
    }

    public void setVibracao(boolean vibracao) {
        this.vibracao = vibracao;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }
}
