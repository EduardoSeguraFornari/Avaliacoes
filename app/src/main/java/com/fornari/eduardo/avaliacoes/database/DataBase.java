package com.fornari.eduardo.avaliacoes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by dufor on 10/09/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context){
        super(context, "AVALIACOES",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.createTableDisciplina());
        db.execSQL(ScriptSQL.createTableTipoAvaliacao());
        db.execSQL(ScriptSQL.createTableAvaliacao());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
