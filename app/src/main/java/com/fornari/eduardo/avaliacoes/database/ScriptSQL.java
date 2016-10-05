package com.fornari.eduardo.avaliacoes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Disciplina;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

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
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS AVALIACAO ");
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
}
