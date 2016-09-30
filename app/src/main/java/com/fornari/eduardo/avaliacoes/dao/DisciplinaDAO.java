package com.fornari.eduardo.avaliacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Avaliacao;
import com.fornari.eduardo.avaliacoes.model.Disciplina;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo Segura Fornari on 10/09/2016.
 */
public class DisciplinaDAO {

    private Context context;

    public DisciplinaDAO(Context context) {
        this.context = context;
    }

    public int inserir(Disciplina disciplina) {

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Disciplina.DISCIPLINA_NOME, disciplina.getNome());

        int disciplinaId = (int) connection.insertOrThrow("DISCIPLINA", null, values);

        connection.close();

        return disciplinaId;
    }

    public List<Disciplina> buscaDisciplinas(Context context) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<Disciplina> disciplinas = new ArrayList<Disciplina>();

        Cursor cursor = connection.query("DISCIPLINA", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Disciplina disciplina = new Disciplina(
                        cursor.getInt(cursor.getColumnIndex(Disciplina.DISCIPLINA_ID)),
                        cursor.getString(cursor.getColumnIndex(Disciplina.DISCIPLINA_NOME))
                );
                disciplinas.add(disciplina);
            } while (cursor.moveToNext());
        }

        connection.close();

        return disciplinas;
    }

    public Disciplina buscaDisciplinaPorNome(String nome) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String[] campos = {Disciplina.DISCIPLINA_ID, Disciplina.DISCIPLINA_NOME};
        String where = Disciplina.DISCIPLINA_NOME + "='" + nome + "'";

        Cursor cursor = connection.query("DISCIPLINA", campos, where, null, null, null, null, null);

        Disciplina disciplina = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            disciplina = new Disciplina(
                    cursor.getInt(cursor.getColumnIndex(Disciplina.DISCIPLINA_ID)),
                    cursor.getString(cursor.getColumnIndex(Disciplina.DISCIPLINA_NOME))
            );
        }

        connection.close();

        return disciplina;
    }

    public void deletarDisciplinaId(int id) {
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String where = Disciplina.DISCIPLINA_ID + "=" + id;
        connection.delete(Disciplina.DISCIPLINA, where, null);
        dataBase.close();
    }
}