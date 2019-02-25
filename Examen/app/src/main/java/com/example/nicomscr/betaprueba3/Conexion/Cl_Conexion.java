package com.example.nicomscr.betaprueba3.Conexion;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Cl_Conexion extends SQLiteOpenHelper {

    //Tablas
    String SQL_CREATE_TABLE_USUARIOS = "CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, pass TEXT);";
    String SQL_CREATE_TABLE_PACIENTES = "CREATE TABLE pacientes (id INTEGER PRIMARY KEY AUTOINCREMENT, rut TEXT, nombre TEXT, apellido TEXT, edad int, direccion TEXT, medicamento TEXT);";
    String SQL_CREATE_TABLE_MEDICAMENTOS = "CREATE TABLE medicamentos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, laboratorio TEXT, miligramos REAL);";


    public Cl_Conexion (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USUARIOS);
        db.execSQL(SQL_CREATE_TABLE_PACIENTES);
        db.execSQL(SQL_CREATE_TABLE_MEDICAMENTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS pacientes");
        db.execSQL("DROP TABLE IF EXISTS medicamentos");
        onCreate(db);
    }
}
