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
 * Created by dufor on 10/09/2016.
 */
public class DisciplinaDAO {

    private Context context;

    public DisciplinaDAO(Context context){
        this.context = context;
    }

    public  int insert(Disciplina disciplina){

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase conn = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Disciplina.DISCIPLINA_NOME, disciplina.getNome());

        return (int) conn.insertOrThrow("DISCIPLINA",null,values);
    }

    public List<Disciplina> buscaDisciplinas(Context context){
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase conn = dataBase.getWritableDatabase();
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        Cursor cursor = conn.query("DISCIPLINA",null,null,null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Disciplina disciplina = new Disciplina(
                        cursor.getInt(cursor.getColumnIndex(Disciplina.DISCIPLINA_ID)),
                        cursor.getString(cursor.getColumnIndex(Disciplina.DISCIPLINA_NOME))
                );
                disciplinas.add(disciplina);
            }while (cursor.moveToNext());
        }
        return disciplinas;
    }

    public Disciplina buscaDisciplinaPorNome(String nome){
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase conn = dataBase.getWritableDatabase();
        Cursor cursor;
        String[] campos =  {Disciplina.DISCIPLINA_ID, Disciplina.DISCIPLINA_NOME};
        String where = Disciplina.DISCIPLINA_NOME + "='" + nome+"'";
        cursor = conn.query("DISCIPLINA",campos,where, null, null, null, null, null);
        Disciplina disciplina = null;
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            disciplina = new Disciplina(
                cursor.getInt(cursor.getColumnIndex(Disciplina.DISCIPLINA_ID)),
                cursor.getString(cursor.getColumnIndex(Disciplina.DISCIPLINA_NOME))
            );
        }
        conn.close();
        return disciplina;
    }
}