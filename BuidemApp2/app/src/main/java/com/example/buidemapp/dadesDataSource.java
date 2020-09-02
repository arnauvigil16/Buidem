package com.example.buidemapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class dadesDataSource {

    public static final String TABLE_ZONAS = "zonas";
    public static final String ZONA_ID = "_id";
    public static final String ZONA_NOM = "nom_zona";

    public static final String TABLE_TIPUS = "tipus_maquina";
    public static final String TIPUS_ID = "_id";
    public static final String TIPUS_CODI = "tipo";

    public static final String TABLE_MAQUINA = "maquina_client";
    public static final String MAQUINA_ID = "_id";
    public static final String MAQUINA_NOM_CLIENT = "nom_client";
    public static final String MAQUINA_ADRECA = "adreca";
    public static final String MAQUINA_CODI_POSTAL = "codi_postal";
    public static final String MAQUINA_POBLACIO = "poblacio";
    public static final String MAQUINA_TELEFON = "telefon";
    public static final String MAQUINA_EMAIL = "email";
    public static final String MAQUINA_NUM_SERIE_MAQUINA = "num_serie_maquina";
    public static final String MAQUINA_DATA_REVISIO = "data_revisio";
    public static final String MAQUINA_TIPUS = "tipus";
    public static final String MAQUINA_ZONA = "zona";

    private static SQLiteDatabase dbW, dbR;
    private Buidem_Helper helper_bbdd;

    //Contructor
    public dadesDataSource(Context context){
        helper_bbdd = new Buidem_Helper(context);
        dbW = helper_bbdd.getWritableDatabase();
        dbR = helper_bbdd.getReadableDatabase();
    }

    //Devolver todos los campos

    public Cursor totes_zones(){
        return dbR.query(TABLE_ZONAS, new String[]{ZONA_ID,ZONA_NOM},
                null, null,
                null, null, ZONA_ID);
    }

    public Cursor tots_tipus() {
        return dbR.query(TABLE_TIPUS, new String[]{TIPUS_ID,TIPUS_CODI},
                null, null,
                null, null, TIPUS_ID);
    }

    public Cursor totes_maquines() {
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                null, null,
                null, null, MAQUINA_ID);
    }

    //Devolver elemento unico

    public Cursor zona_unica(long id) {
        return dbR.query(TABLE_ZONAS, new String[]{ZONA_ID,ZONA_NOM},
                ZONA_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
    }

    public Cursor tipus_unic(long id) {
        return dbR.query(TABLE_TIPUS, new String[]{TIPUS_ID,TIPUS_CODI},
                TIPUS_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
    }


    public Cursor buscarMaquinas(String id) {
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                MAQUINA_NUM_SERIE_MAQUINA + "=?", new String[]{id},
                null, null, null);
    }

    public Cursor ordenarMaquinas(int orden){
        Cursor cursor = null;

        if (orden == 0){
            cursor = dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                    null, null,
                    null, null, MAQUINA_NOM_CLIENT);


        }
        else if (orden == 1){
            cursor = dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                    null, null,
                    null, null, MAQUINA_ZONA);
        }
        else if (orden == 2){
            cursor = dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                    null, null,
                    null, null, MAQUINA_POBLACIO);
        }
        else if (orden == 3){
            cursor = dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                    null, null,
                    null, null, MAQUINA_ADRECA);
        }
        else if (orden == 4){
            cursor = dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO, MAQUINA_TIPUS, MAQUINA_ZONA},
                    null, null,
                    null, null, MAQUINA_DATA_REVISIO);
        }

        return cursor;
                

    }


    //Añadir elementos en la base de datos

    public void addZona(String nom){
        ContentValues values = new ContentValues();

        values.put(ZONA_NOM, nom);

        dbW.insert(TABLE_ZONAS,null,values);
    }


    public void addTipus(String codi){
        ContentValues values = new ContentValues();

        values.put(TIPUS_CODI, codi);

        dbW.insert(TABLE_TIPUS,null,values);
    }

    public void addMaquina(String nom_client, String adreca, String codi_postal, String poblacio, String telefon, String email, String num_serie_maquina, String data_revisio, String tipus, String zona){
        ContentValues values = new ContentValues();

        values.put(MAQUINA_NOM_CLIENT, nom_client);
        values.put(MAQUINA_ADRECA, adreca);
        values.put(MAQUINA_CODI_POSTAL, codi_postal);
        values.put(MAQUINA_POBLACIO, poblacio);
        values.put(MAQUINA_TELEFON, telefon);
        values.put(MAQUINA_EMAIL, email);
        values.put(MAQUINA_NUM_SERIE_MAQUINA, num_serie_maquina);
        values.put(MAQUINA_DATA_REVISIO, data_revisio);
        values.put(MAQUINA_TIPUS, tipus);
        values.put(MAQUINA_ZONA, zona);


        dbW.insert(TABLE_MAQUINA,null,values);
    }

    //Eliminar elementos en la base de datos

    public void eliminarZona(long id) {
        dbW.delete(TABLE_ZONAS, ZONA_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void eliminarTipus(long id) {
        dbW.delete(TABLE_TIPUS, TIPUS_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void eliminarMaquina(long id) {
        dbW.delete(TABLE_MAQUINA, MAQUINA_ID + " = ?", new String[] { String.valueOf(id) });
    }

    //Actualizar elementos en la base de datos

    public void updateZona(long id, String nom){
        ContentValues values = new ContentValues();

        values.put(ZONA_NOM, nom);

        dbW.update(TABLE_ZONAS,values, ZONA_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void updateTipus(long id, String codi){
        ContentValues values = new ContentValues();

        values.put(TIPUS_CODI, codi);

        dbW.update(TABLE_TIPUS,values, TIPUS_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public void updateMaquina(long id, String nom_client, String adreca, String codi_postal, String poblacio, String telefon, String email, String num_serie_maquina, String data_revisio, String tipus, String zona){
        ContentValues values = new ContentValues();

        values.put(MAQUINA_NOM_CLIENT, nom_client);
        values.put(MAQUINA_ADRECA, adreca);
        values.put(MAQUINA_CODI_POSTAL, codi_postal);
        values.put(MAQUINA_POBLACIO, poblacio);
        values.put(MAQUINA_TELEFON, telefon);
        values.put(MAQUINA_EMAIL, email);
        values.put(MAQUINA_NUM_SERIE_MAQUINA, num_serie_maquina);
        values.put(MAQUINA_DATA_REVISIO, data_revisio);

        dbW.update(TABLE_MAQUINA,values, MAQUINA_ID + " = ?", new String[] { String.valueOf(id) });
    }

    //Comprovar que no es repeteixi el id
    public Cursor MaquinaRepetida(String itemCode) {
        return dbR.query(TABLE_MAQUINA, new String[]{MAQUINA_ID,MAQUINA_NOM_CLIENT,MAQUINA_ADRECA,MAQUINA_CODI_POSTAL,MAQUINA_POBLACIO,MAQUINA_TELEFON,MAQUINA_EMAIL,MAQUINA_NUM_SERIE_MAQUINA,MAQUINA_DATA_REVISIO , MAQUINA_TIPUS, MAQUINA_ZONA},
                MAQUINA_NUM_SERIE_MAQUINA+ "=?", new String[]{String.valueOf(itemCode)},
                null, null, null);
    }






    //Devolver todos los articulos para el spinner

    public List<String> spinnerTipus(){
        List<String> labels = new ArrayList<String>();

        Cursor cursor = dbR.query(TABLE_TIPUS, new String[]{TIPUS_ID,TIPUS_CODI},
                null, null,
                null, null, TIPUS_ID);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return labels;
    }

    public List<String> spinnerZones(){
        List<String> labels = new ArrayList<String>();

        Cursor cursor = dbR.query(TABLE_ZONAS, new String[]{ZONA_ID,ZONA_NOM},
                null, null,
                null, null, ZONA_ID);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return labels;
    }
}