package com.fornari.eduardo.avaliacoes.database;

import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Disciplina;
import com.fornari.eduardo.avaliacoes.model.Notificacao;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

import java.text.SimpleDateFormat;

/**
 * Created by Eduardo Segura Fornari on 10/09/2016.
 */
public class ScriptSQL {

    public static String createTableDisciplina() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + Disciplina.DISCIPLINA + " ");
        sqlBuilder.append("( ");
        sqlBuilder.append(" " + Disciplina.DISCIPLINA_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" " + Disciplina.DISCIPLINA_NOME + " VARCHAR (200) ");
        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

    public static String createTableTipoAvaliacao() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + TipoAvaliacao.TIPO_AVALIACAO + " ");
        sqlBuilder.append("( ");
        sqlBuilder.append(" " + TipoAvaliacao.TIPO_AVALIACAO__ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" " + TipoAvaliacao.TIPO_AVALIACAO_NOME + " VARCHAR (200), ");
        sqlBuilder.append(" " + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR + " INTEGER, ");
        sqlBuilder.append(" " + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO + " INTEGER, ");
        sqlBuilder.append(" " + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO + " VARCHAR (200) ");
        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

    public static String createTableAvaliacao() {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + Avaliacao.AVALIACAO + " ");
        sqlBuilder.append("( ");
        sqlBuilder.append(" " + Avaliacao.AVALIACAO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" " + Avaliacao.AVALIACAO_DATA + " DATE, ");
        sqlBuilder.append(" " + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + " INTEGER, ");
        sqlBuilder.append(" " + Avaliacao.AVALIACAO_OBSERVACAO + " VARCHAR (200), ");
        sqlBuilder.append(" " + Avaliacao.AVALIACAO_DISCIPLINA__ID + " INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(" + Avaliacao.AVALIACAO_DISCIPLINA__ID + ") REFERENCES " + TipoAvaliacao.TIPO_AVALIACAO + "(" + TipoAvaliacao.TIPO_AVALIACAO__ID + "), ");
        sqlBuilder.append("FOREIGN KEY(" + Avaliacao.AVALIACAO_DISCIPLINA__ID + ") REFERENCES " + Disciplina.DISCIPLINA + "(" + Disciplina.DISCIPLINA_ID + ") ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String createTableNotificacao() {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + Notificacao.NOTIFICACAO + " ");
        sqlBuilder.append("( ");
        sqlBuilder.append(" " + Notificacao.NOTIFICACAO_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" " + Notificacao.NOTIFICACAO_NOTIFICAR + " INTEGER, ");
        sqlBuilder.append(" " + Notificacao.NOTIFICACAO_SOM + " INTEGER, ");
        sqlBuilder.append(" " + Notificacao.NOTIFICACAO_VIBRACAO + " INTEGER, ");
        sqlBuilder.append(" " + Notificacao.NOTIFICACAO_HORARIO + " DATE ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String insertNotificacaoPadrao() {
        Notificacao notificacao = new Notificacao();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        String hora = simpleDateFormat.format(notificacao.getHorario());

//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(notificacao.getHorario());
//        java.sql.Timestamp hora = new java.sql.Timestamp(calendar.getTimeInMillis());

//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        String hora = dateFormat.format(notificacao.getHorario());

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO " + Notificacao.NOTIFICACAO + " ");
        sqlBuilder.append("(");
        sqlBuilder.append(Notificacao.NOTIFICACAO_NOTIFICAR + ", ");
        sqlBuilder.append(Notificacao.NOTIFICACAO_SOM + ", ");
        sqlBuilder.append(Notificacao.NOTIFICACAO_VIBRACAO + ", ");
        sqlBuilder.append(Notificacao.NOTIFICACAO_HORARIO);
        sqlBuilder.append(") ");
        sqlBuilder.append("VALUES ");
        sqlBuilder.append("(");
        sqlBuilder.append((notificacao.isNotificar() ? 1 : 0) + ", ");
        sqlBuilder.append((notificacao.isSom() ? 1 : 0) + ", ");
        sqlBuilder.append((notificacao.isVibracao() ? 1 : 0) + ", ");
        sqlBuilder.append("'" + hora + "'");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

}