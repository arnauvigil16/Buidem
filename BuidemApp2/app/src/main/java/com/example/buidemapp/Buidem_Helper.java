package com.example.buidemapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Buidem_Helper extends SQLiteOpenHelper {

    // Versio de la base de dades
    private static final int database_VERSION = 1;

    // Nom base de dades
    private static final String database_NAME = "Buidem";

    public Buidem_Helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    private String CREATE_MAQUINA =
            "CREATE TABLE maquina_client ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom_client TEXT NOT NULL," +
                    "adreca TEXT NOT NULL," +
                    "codi_postal TEXT NOT NULL," +
                    "poblacio TEXT NOT NULL," +
                    "telefon TEXT," +
                    "email TEXT," +
                    "num_serie_maquina REAL NOT NULL," +
                    "data_revisio DATETIME," +
                    "tipus REAL NOT NULL," +
                    "zona REAL NOT NULL," +
                    "FOREIGN KEY(zona) REFERENCES zonas(_id) ON DELETE RESTRICT," +
                    "FOREIGN KEY(tipus) REFERENCES tipus_maquina(_id) ON DELETE RESTRICT)";

    private String CREATE_ZONAS =
            "CREATE TABLE zonas ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom_zona TEXT)";

    private String CREATE_TIPUS_MAQUINA =
            "CREATE TABLE tipus_maquina ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tipo TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ZONAS);
        db.execSQL(CREATE_TIPUS_MAQUINA);
        db.execSQL(CREATE_MAQUINA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}