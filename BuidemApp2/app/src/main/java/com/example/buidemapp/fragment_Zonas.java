package com.example.buidemapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class fragment_Zonas extends Fragment {

    public static MainActivity context;
    private static dadesDataSource bd;
    private adapterTodoListFilterFragmentZonas scTasks;
    private View view;

    private static String[] from = new String[]{bd.ZONA_NOM};
    private static int[] to = new int[]{R.id.lblNom_Zona};

    public fragment_Zonas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zonas, container, false);
        cargarZonas(view);
        ImageView add = view.findViewById(R.id.imAddZona);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View regisText = factory.inflate(R.layout.layout_zona, null);
                builder.setTitle("Añadir nueva Zona");

                builder.setView(regisText)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText nomZona = regisText.findViewById(R.id.edtNombreZona);
                                String nomzona = nomZona.getText().toString();

                                if (nomZona.equals("")){
                                    Toast.makeText(getContext(),"Introdueix totes les dades",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    bd.addZona(nomzona);
                                    cargarZonas(view);
                                    Snackbar.make(getView(), "S'ha afegit la zona amb nom: " + nomzona, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.show();
            }
        });
        return view;
    }

    public void cargarZonas(View view){
        bd = new dadesDataSource(getActivity());
        Cursor cursorTipus = bd.totes_zones();
        scTasks = new adapterTodoListFilterFragmentZonas(getActivity(), R.layout.zona, cursorTipus, from, to, 1, fragment_Zonas.this);
        ListView lvZonas = view.findViewById(R.id.lista_Zonas);
        lvZonas.setAdapter(scTasks);
    }

    public void deleteZona(final long identificador, final String Nombre_Zona) {

        // Preguntem si esta segur
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Eliminar la zona?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.eliminarZona(identificador);
                Snackbar.make(getView(), "S'ha eliminat la zona amb nom: " + Nombre_Zona, Snackbar.LENGTH_LONG).show();
                todesZones();
            }
        });

        builder.setNegativeButton("No", null);

        builder.show();

    }

    public void editZona(final String Nombre_Zona, final long identificador) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View regisText = factory.inflate(R.layout.layout_zona, null);
        builder.setTitle("Modificar Zona");
        final EditText nomZona = regisText.findViewById(R.id.edtNombreZona);

        nomZona.setText(Nombre_Zona);

        builder.setView(regisText)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String nomzona = nomZona.getText().toString();

                        if (nomZona.equals("") ){
                            Toast.makeText(getContext(),"Introdueix totes les dades",Toast.LENGTH_LONG).show();
                        }
                        else {
                            bd.updateZona(identificador, nomzona);
                            cargarZonas(view);
                            Snackbar.make(getView(), "S'ha modificat la zona amb nom: " + nomzona, Snackbar.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    private void todesZones() {
        Cursor cursorArticles = bd.totes_zones();
        scTasks.changeCursor(cursorArticles);
        scTasks.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

class adapterTodoListFilterFragmentZonas extends android.widget.SimpleCursorAdapter {

    private fragment_Zonas fragament_zonas;
    public static MainActivity context;

    public adapterTodoListFilterFragmentZonas(Context context, int layout, Cursor c, String[] from, int[] to, int flags, fragment_Zonas fragment) {
        super(context, layout, c, from, to, flags);
        fragament_zonas = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = super.getView(position, convertView, parent);

        Cursor linia = (Cursor) getItem(position);

        final long identificador = linia.getInt(linia.getColumnIndexOrThrow(dadesDataSource.ZONA_ID));
        final String nom_zona = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.ZONA_NOM));

        ImageView delete = view.findViewById(R.id.imDeleteZonas);
        ImageView edit = view.findViewById(R.id.imEditZonas);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragament_zonas.editZona(nom_zona, identificador);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragament_zonas.deleteZona(identificador, nom_zona);
            }
        });
        return view;
    }
}