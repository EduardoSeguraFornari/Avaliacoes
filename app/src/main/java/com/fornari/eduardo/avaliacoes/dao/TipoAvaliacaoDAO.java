package com.fornari.eduardo.avaliacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fornari.eduardo.avaliacoes.database.DataBase;
import com.fornari.eduardo.avaliacoes.model.Disciplina;
import com.fornari.eduardo.avaliacoes.model.TipoAvaliacao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo Segura Fornari on 14/09/2016.
 */
public class TipoAvaliacaoDAO {
    private Context context;

    public TipoAvaliacaoDAO(Context context){
        this.context = context;
    }

    public  int inserir(TipoAvaliacao tipoAvaliacao){

        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TipoAvaliacao.TIPO_AVALIACAO_NOME, tipoAvaliacao.getNome());

        int tipoAvaliacaoId = (int)connection.insertOrThrow(TipoAvaliacao.TIPO_AVALIACAO, null,values);

        connection.close();

        return tipoAvaliacaoId;
    }

    public List<TipoAvaliacao> buscaTiposDeAvaliacao(){
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        List<TipoAvaliacao> tipoAvaliacoes = new ArrayList<TipoAvaliacao>();

        Cursor cursor = connection.query(TipoAvaliacao.TIPO_AVALIACAO,null,null,null,null,null,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{

                TipoAvaliacao tipoAvaliacao = new TipoAvaliacao(
                        cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO__ID)),
                        cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOME))
                );
                tipoAvaliacoes.add(tipoAvaliacao);
            }while (cursor.moveToNext());
        }

        connection.close();

        return tipoAvaliacoes;
    }

    public TipoAvaliacao buscaTipoAvaliacaoPorNome(String nome){
        DataBase dataBase = new DataBase(context);
        SQLiteDatabase connection = dataBase.getWritableDatabase();

        String[] campos =  {TipoAvaliacao.TIPO_AVALIACAO__ID,TipoAvaliacao.TIPO_AVALIACAO_NOME};
        String where = TipoAvaliacao.TIPO_AVALIACAO_NOME + "='" + nome+"'";

        Cursor cursor = connection.query(TipoAvaliacao.TIPO_AVALIACAO,campos,where, null, null, null, null, null);

        TipoAvaliacao tipoAvaliacao = null;

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            tipoAvaliacao = new TipoAvaliacao(
                    cursor.getInt(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO__ID)),
                    cursor.getString(cursor.getColumnIndex(TipoAvaliacao.TIPO_AVALIACAO_NOME))
            );
        }

        connection.close();

        return tipoAvaliacao;
    }
}