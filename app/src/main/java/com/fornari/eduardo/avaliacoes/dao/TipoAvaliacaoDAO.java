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
import java.util.List;

/**
 * Created by Eduardo Segura Fornari on 14/09/2016.
 */
public class TipoAvaliacaoDAO {
    private Context context;

    public TipoAvaliacaoDAO(Context context) {
        this.context = context;
    }

    public int inserir(TipoAvaliacao tipoAvaliacao) {

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TipoAvaliacao.TIPO_AVALIACAO_NOME, tipoAvaliacao.getNome());
        values.put(TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR, tipoAvaliacao.isNotificar() ? 1 : 0);
        values.put(TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO, tipoAvaliacao.getAntecedenciaNotificacao());
        values.put(TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO, tipoAvaliacao.getDescricao());

        int tipoAvaliacaoId = (int) connection.insertOrThrow(TipoAvaliacao.TIPO_AVALIACAO, null, values);

        connection.close();

        return tipoAvaliacaoId;
    }

    public List<TipoAvaliacao> buscaTiposAvaliacao() {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<TipoAvaliacao> tipoAvaliacoes = new ArrayList<TipoAvaliacao>();

        Cursor cursor = connection.query(TipoAvaliacao.TIPO_AVALIACAO, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                TipoAvaliacao tipoAvaliacao = new TipoAvaliacao(
                        cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO__ID)),
                        cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOME)),
                        cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR)) == 1,
                        cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO)),
                        cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO))
                );
                tipoAvaliacoes.add(tipoAvaliacao);
            } while (cursor.moveToNext());
        }

        connection.close();

        return tipoAvaliacoes;
    }

    public TipoAvaliacao buscaTipoAvaliacaoPorNome(String nome) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String[] campos = {TipoAvaliacao.TIPO_AVALIACAO__ID, TipoAvaliacao.TIPO_AVALIACAO_NOME, TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR, TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO, TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO};
        String where = TipoAvaliacao.TIPO_AVALIACAO_NOME + "='" + nome + "'";

        Cursor cursor = connection.query(TipoAvaliacao.TIPO_AVALIACAO, campos, where, null, null, null, null, null);

        TipoAvaliacao tipoAvaliacao = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tipoAvaliacao = new TipoAvaliacao(
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO__ID)),
                    cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOME)),
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR)) == 1,
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO)),
                    cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO))
            );
        }

        connection.close();

        return tipoAvaliacao;
    }

    public TipoAvaliacao buscaTipoAvaliacaoId(int id) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String[] campos = {TipoAvaliacao.TIPO_AVALIACAO__ID, TipoAvaliacao.TIPO_AVALIACAO_NOME, TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR, TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO, TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO};
        String where = TipoAvaliacao.TIPO_AVALIACAO__ID + "='" + id + "'";

        Cursor cursor = connection.query(TipoAvaliacao.TIPO_AVALIACAO, campos, where, null, null, null, null, null);

        TipoAvaliacao tipoAvaliacao = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tipoAvaliacao = new TipoAvaliacao(
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO__ID)),
                    cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOME)),
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR)) == 1,
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO)),
                    cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO))
            );
        }

        connection.close();

        return tipoAvaliacao;
    }

    public void atualizaTipoAvaliacao(int id, TipoAvaliacao tipoAvaliacao) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = TipoAvaliacao.TIPO_AVALIACAO__ID + "=" + id;

        ContentValues valores = new ContentValues();
        valores.put(TipoAvaliacao.TIPO_AVALIACAO__ID, tipoAvaliacao.getId());
        valores.put(TipoAvaliacao.TIPO_AVALIACAO_NOME, tipoAvaliacao.getNome());
        valores.put(TipoAvaliacao.TIPO_AVALIACAO_NOTIFICAR, tipoAvaliacao.isNotificar() ? 1 : 0);
        valores.put(TipoAvaliacao.TIPO_AVALIACAO_ANTECEDENCIA_NOTIFICACAO, tipoAvaliacao.getAntecedenciaNotificacao());
        valores.put(TipoAvaliacao.TIPO_AVALIACAO_DESCRICAO, tipoAvaliacao.getDescricao());

        connection.update(TipoAvaliacao.TIPO_AVALIACAO, valores, where, null);

        connection.close();
    }

    public void deletarTipoAvaliacaoId(int id) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = TipoAvaliacao.TIPO_AVALIACAO__ID + "=" + id;
        connection.delete(TipoAvaliacao.TIPO_AVALIACAO, where, null);
        dataBase.close();
    }
}