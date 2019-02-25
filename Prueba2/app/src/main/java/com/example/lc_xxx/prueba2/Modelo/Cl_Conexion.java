package com.example.lc_xxx.prueba2.Modelo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Cl_Conexion extends SQLiteOpenHelper {

    //Tablas
    String SQL_CREATE_TABLE_USUARIOS = "CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, pass TEXT);";
    String SQL_CREATE_TABLE_MASCOTAS = "CREATE TABLE mascotas (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, raza TEXT, tipo INTEGER, edad int);";


    public Cl_Conexion (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USUARIOS);
        db.execSQL(SQL_CREATE_TABLE_MASCOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS mascotas");
        onCreate(db);
    }
}
