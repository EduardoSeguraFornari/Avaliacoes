package com.fornari.eduardo.avaliacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Disciplina;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo Segura Fornari on 17/09/2016.
 */
public class AvaliacaoDAO {

    private Context context;

    public AvaliacaoDAO(Context context) {
        this.context = context;
    }

    public int inserir(Avaliacao avaliacao) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Avaliacao.AVALIACAO_DATA, avaliacao.getData().getTime());
        values.put(Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID, avaliacao.getTipoAvaliacaoId());
        values.put(Avaliacao.AVALIACAO_DISCIPLINA__ID, avaliacao.getDisciplinaId());
        values.put(Avaliacao.AVALIACAO_OBSERVACAO, avaliacao.getObservacao());

        int avaliacaoId = (int) connection.insertOrThrow(Avaliacao.AVALIACAO, null, values);

        connection.close();

        return avaliacaoId;
    }

    public List<Avaliacao> buscaAvaliacoesDisciplina(int disciplinaId) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<Avaliacao> avaliacaos = new ArrayList<Avaliacao>();

        String query =
                "SELECT A." + Avaliacao.AVALIACAO_ID + ", A." + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + ", A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + ", A." + Avaliacao.AVALIACAO_DATA + ", A." + Avaliacao.AVALIACAO_OBSERVACAO + "," +
                        " T." + TipoAvaliacao.TIPO_AVALIACAO__ID + ", T." + TipoAvaliacao.TIPO_AVALIACAO_NOME + ", T." + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR + ", T." + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO + ", T." + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO + "," +
                        " D." + Disciplina.DISCIPLINA_ID + ", D." + Disciplina.DISCIPLINA_NOME +
                        " FROM " + Avaliacao.AVALIACAO + " as A" +
                        " INNER JOIN " + TipoAvaliacao.TIPO_AVALIACAO + " as T" +
                        " on A." + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + " = T." + TipoAvaliacao.TIPO_AVALIACAO__ID +
                        " INNER JOIN " + Disciplina.DISCIPLINA + " as D" +
                        " on A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + " = D." + Disciplina.DISCIPLINA_ID +
                        " WHERE A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + " = " + disciplinaId;

        Cursor cursor = connection.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int tipoAvaliacaoId = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO__ID));
                String nomeTipoAvaliacao = cursor.getString(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_NOME));
                boolean notificarTipoAvaliacao = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR)) == 1;
                int antecedenciaNotificacaoTipoAvaliacao = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO));
                String descricaoTipoAvaliacao = cursor.getString(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO));
                TipoAvaliacao tipoAvaliacao = new TipoAvaliacao(tipoAvaliacaoId, nomeTipoAvaliacao, notificarTipoAvaliacao, antecedenciaNotificacaoTipoAvaliacao, descricaoTipoAvaliacao);

                Date dataAvaliacao = new Date(cursor.getLong(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_DATA)));

                String nomeDisciplina = cursor.getString(cursor.getColumnIndex("D." + Disciplina.DISCIPLINA_NOME));
                Disciplina disciplina = new Disciplina(disciplinaId, nomeDisciplina);

                int avaliacaoId = cursor.getInt(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_ID));

                String avaliacaoObservacao = cursor.getString(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_OBSERVACAO));

                Avaliacao avaliacao = new Avaliacao(avaliacaoId, tipoAvaliacao, dataAvaliacao, avaliacaoObservacao, disciplina);

                avaliacaos.add(avaliacao);
            } while (cursor.moveToNext());
        }

        connection.close();

        return avaliacaos;
    }


    public Avaliacao buscaAvaliacaoID(int id) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

        String query =
                "SELECT A." + Avaliacao.AVALIACAO_ID + ", A." + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + ", A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + ", A." + Avaliacao.AVALIACAO_DATA + ", A." + Avaliacao.AVALIACAO_OBSERVACAO + "," +
                        " T." + TipoAvaliacao.TIPO_AVALIACAO__ID + ", T." + TipoAvaliacao.TIPO_AVALIACAO_NOME + ", T." + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR + ", T." + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO + ", T." + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO + "," +
                        " D." + Disciplina.DISCIPLINA_ID + ", D." + Disciplina.DISCIPLINA_NOME +
                        " FROM " + Avaliacao.AVALIACAO + " as A" +
                        " INNER JOIN " + TipoAvaliacao.TIPO_AVALIACAO + " as T" +
                        " on A." + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + " = T." + TipoAvaliacao.TIPO_AVALIACAO__ID +
                        " INNER JOIN " + Disciplina.DISCIPLINA + " as D" +
                        " on A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + " = D." + Disciplina.DISCIPLINA_ID +
                        " WHERE A." + Avaliacao.AVALIACAO_ID + " = " + id;

        Cursor cursor = connection.rawQuery(query, null);

        Avaliacao avaliacao = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            int tipoAvaliacaoId = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO__ID));
            String nomeTipoAvaliacao = cursor.getString(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_NOME));
            boolean notificarTipoAvaliacao = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR)) == 1;
            int antecedenciaNotificacaoTipoAvaliacao = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO));
            String descricaoTipoAvaliacao = cursor.getString(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO));
            TipoAvaliacao tipoAvaliacao = new TipoAvaliacao(tipoAvaliacaoId, nomeTipoAvaliacao, notificarTipoAvaliacao, antecedenciaNotificacaoTipoAvaliacao, descricaoTipoAvaliacao);

            Date dataAvaliacao = new Date(cursor.getLong(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_DATA)));

            String nomeDisciplina = cursor.getString(cursor.getColumnIndex("D." + Disciplina.DISCIPLINA_NOME));
            int disciplinaId = cursor.getInt(cursor.getColumnIndex("D." + Disciplina.DISCIPLINA_ID));
            Disciplina disciplina = new Disciplina(disciplinaId, nomeDisciplina);

            String avaliacaoObservacao = cursor.getString(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_OBSERVACAO));

            avaliacao = new Avaliacao(id, tipoAvaliacao, dataAvaliacao, avaliacaoObservacao, disciplina);
        }

        connection.close();

        return avaliacao;
    }

    public void atualizaAvaliacao(int id, Avaliacao avaliacao) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = Avaliacao.AVALIACAO_ID + "=" + id;

        ContentValues valores = new ContentValues();
        valores.put(Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID, avaliacao.getTipoAvaliacaoId());
        valores.put(Avaliacao.AVALIACAO_DATA, avaliacao.getData().getTime());
        valores.put(Avaliacao.AVALIACAO_OBSERVACAO, avaliacao.getObservacao());
        valores.put(Avaliacao.AVALIACAO_DISCIPLINA__ID, avaliacao.getDisciplinaId());

        connection.update(Avaliacao.AVALIACAO, valores, where, null);

        connection.close();
    }

    public List<Avaliacao> buscaAvaliacoes() {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<Avaliacao> avaliacaos = new ArrayList<Avaliacao>();

        String query =
                "SELECT A." + Avaliacao.AVALIACAO_ID + ", A." + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + ", A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + ", A." + Avaliacao.AVALIACAO_DATA + ", A." + Avaliacao.AVALIACAO_OBSERVACAO + "," +
                        " T." + TipoAvaliacao.TIPO_AVALIACAO__ID + ", T." + TipoAvaliacao.TIPO_AVALIACAO_NOME + ", T." + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR + ", T." + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO + ", T." + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO + "," +
                        " D." + Disciplina.DISCIPLINA_ID + ", D." + Disciplina.DISCIPLINA_NOME +
                        " FROM " + Avaliacao.AVALIACAO + " as A" +
                        " INNER JOIN " + TipoAvaliacao.TIPO_AVALIACAO + " as T" +
                        " on A." + Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + " = T." + TipoAvaliacao.TIPO_AVALIACAO__ID +
                        " INNER JOIN " + Disciplina.DISCIPLINA + " as D" +
                        " on A." + Avaliacao.AVALIACAO_DISCIPLINA__ID + " = D." + Disciplina.DISCIPLINA_ID;

        Cursor cursor = connection.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int tipoAvaliacaoId = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO__ID));
                String nomeTipoAvaliacao = cursor.getString(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_NOME));
                boolean notificarTipoAvaliacao = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR)) == 1;
                int antecedenciaNotificacaoTipoAvaliacao = cursor.getInt(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO));
                String descricaoTipoAvaliacao = cursor.getString(cursor.getColumnIndex("T." + TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO));
                TipoAvaliacao tipoAvaliacao = new TipoAvaliacao(tipoAvaliacaoId, nomeTipoAvaliacao, notificarTipoAvaliacao, antecedenciaNotificacaoTipoAvaliacao, descricaoTipoAvaliacao);

                Date dataAvaliacao = new Date(cursor.getLong(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_DATA)));

                String nomeDisciplina = cursor.getString(cursor.getColumnIndex("D." + Disciplina.DISCIPLINA_NOME));
                int disciplinaId = cursor.getInt(cursor.getColumnIndex("D." + Disciplina.DISCIPLINA_ID));
                Disciplina disciplina = new Disciplina(disciplinaId, nomeDisciplina);

                int avaliacaoId = cursor.getInt(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_ID));

                String avaliacaoObservacao = cursor.getString(cursor.getColumnIndex("A." + Avaliacao.AVALIACAO_OBSERVACAO));

                Avaliacao avaliacao = new Avaliacao(avaliacaoId, tipoAvaliacao, dataAvaliacao, avaliacaoObservacao, disciplina);

                avaliacaos.add(avaliacao);
            } while (cursor.moveToNext());
        }

        connection.close();

        return avaliacaos;
    }

    public void deletarAvaliacaoId(int id) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = Avaliacao.AVALIACAO_ID + "=" + id;
        connection.delete(Avaliacao.AVALIACAO, where, null);
        dataBase.close();
    }

    public void deletarAvaliacoesDisciplina(int disciplinaId) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = Avaliacao.AVALIACAO_DISCIPLINA__ID + "=" + disciplinaId;
        connection.delete(Avaliacao.AVALIACAO, where, null);
        dataBase.close();
    }

    public void deletarAvaliacoesTipoAvaliacao(int tipoAvaliacaoId) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = Avaliacao.AVALIACAO_TIPO_AVALIACAO__ID + "=" + tipoAvaliacaoId;
        connection.delete(Avaliacao.AVALIACAO, where, null);
        dataBase.close();
    }
}