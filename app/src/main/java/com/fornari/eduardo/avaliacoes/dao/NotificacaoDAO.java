package com.fornari.eduardo.avaliacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Notificacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dufor on 11/10/2016.
 */
public class NotificacaoDAO {
    private Context context;

    public void inserirNotificacaoDefault() {

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        Notificacao notificacao = new Notificacao();

        ContentValues values = new ContentValues();

        values.put(Notificacao.NOTIFICACAO_NOTIFICAR, notificacao.isNotificar() ? 1 : 0);
        values.put(Notificacao.NOTIFICACAO_SOM, notificacao.isNotificar() ? 1 : 0);
        values.put(Notificacao.NOTIFICACAO_VIBRACAO, notificacao.isNotificar() ? 1 : 0);
        values.put(Notificacao.NOTIFICACAO_HORARIO, notificacao.getHorario().getTime());

        connection.insertOrThrow(Notificacao.NOTIFICACAO, null, values);

        connection.close();

    }

    public NotificacaoDAO(Context context) {
        this.context = context;
    }

    public Notificacao buscaNotificacao() {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

        String query =
                "SELECT * FROM " + Notificacao.NOTIFICACAO;

        Cursor cursor = connection.rawQuery(query, null);

        Notificacao notificacao = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            int notificacaoId = cursor.getInt(cursor.getColumnIndex(Notificacao.NOTIFICACAO_ID));
            boolean notificar = cursor.getInt(cursor.getColumnIndex(Notificacao.NOTIFICACAO_NOTIFICAR)) == 1;
            boolean som = cursor.getInt(cursor.getColumnIndex(Notificacao.NOTIFICACAO_SOM)) == 1;
            boolean vibracao = cursor.getInt(cursor.getColumnIndex(Notificacao.NOTIFICACAO_VIBRACAO)) == 1;
            Date hora = new Date(cursor.getLong(cursor.getColumnIndex(Notificacao.NOTIFICACAO_HORARIO)));

            notificacao = new Notificacao(notificar, som, vibracao, hora);
            notificacao.setId(notificacaoId);
        }

        connection.close();

        return notificacao;
    }

    public void atualizaNotificacao(Notificacao notificacao) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = Notificacao.NOTIFICACAO_ID + "=" + notificacao.getId();

        ContentValues valores = new ContentValues();
        valores.put(Notificacao.NOTIFICACAO_ID, notificacao.getId());
        valores.put(Notificacao.NOTIFICACAO_NOTIFICAR, notificacao.isNotificar() ? 1 : 0);
        valores.put(Notificacao.NOTIFICACAO_SOM, notificacao.isSom() ? 1 : 0);
        valores.put(Notificacao.NOTIFICACAO_VIBRACAO, notificacao.isVibracao() ? 1 : 0);
        valores.put(Notificacao.NOTIFICACAO_HORARIO, notificacao.getHorario().getTime());

        connection.update(Notificacao.NOTIFICACAO, valores, where, null);

        connection.close();
    }
}
