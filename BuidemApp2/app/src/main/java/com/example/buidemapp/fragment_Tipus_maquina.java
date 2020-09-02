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

public class fragment_Tipus_maquina extends Fragment {

    public static MainActivity context;
    private static dadesDataSource bd;
    private adapterTodoListFilterFragmentTipus_Maquina scTasks;
    private View view;

    private static String[] from = new String[]{bd.TIPUS_CODI};
    private static int[] to = new int[]{R.id.lblTipoMaquina};

    public fragment_Tipus_maquina() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tipus_maquina, container, false);
        cargarTipus(view);
        ImageView addTipus = view.findViewById(R.id.imAfegirTipus);
        addTipus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View regisText = factory.inflate(R.layout.layout_tipus, null);
                builder.setTitle("Añadir nuevo Tipus");

                builder.setView(regisText)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText tipus = regisText.findViewById(R.id.edTipusMaquina);
                                String tipus_maquina = tipus.getText().toString();

                                if (tipus_maquina.equals("")){
                                    Toast.makeText(getContext(),"Introdueix totes les dades",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    bd.addTipus(tipus_maquina);
                                    cargarTipus(view);
                                    Snackbar.make(getView(), "S'ha afegit el tipus amb nom: " + tipus_maquina, Snackbar.LENGTH_LONG).show();
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

    public void editTipus( final String TipusMaquina, final long identificador) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View regisText = factory.inflate(R.layout.layout_tipus, null);
        builder.setTitle("Modificar Tipus de Maquina");

        final EditText tipusMaquina = regisText.findViewById(R.id.edTipusMaquina);
        tipusMaquina.setText(TipusMaquina);

        builder.setView(regisText)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String tipus = tipusMaquina.getText().toString();


                        if (tipus.equals("")){
                            Toast.makeText(getContext(),"Introdueix totes les dades",Toast.LENGTH_LONG).show();
                        }
                        else {
                            bd.updateTipus(identificador, tipus);
                            cargarTipus(view);
                            Snackbar.make(getView(), "S'ha modificat el tipus amb nom: " + tipus, Snackbar.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    public void deleteTipus(final long identificador, final String Nom_Tipus) {

        // Preguntem si esta segur
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Eliminar el tipus?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                bd.eliminarTipus(identificador);
                Snackbar.make(getView(), "S'ha eliminat el tipus amb nom: " + Nom_Tipus, Snackbar.LENGTH_LONG).show();
                totsTipus();
            }
        });

        builder.setNegativeButton("No", null);
        builder.show();
    }

    public void cargarTipus(View view){
        bd = new dadesDataSource(getActivity());
        Cursor cursorTipus = bd.tots_tipus();
        scTasks = new adapterTodoListFilterFragmentTipus_Maquina(getActivity(), R.layout.tipus, cursorTipus, from, to, 1,fragment_Tipus_maquina.this);
        ListView lvTipus = view.findViewById(R.id.list_TipusMaquines);
        lvTipus.setAdapter(scTasks);
    }

    private void totsTipus() {
        Cursor cursorArticles = bd.tots_tipus();
        scTasks.changeCursor(cursorArticles);
        scTasks.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

class adapterTodoListFilterFragmentTipus_Maquina extends android.widget.SimpleCursorAdapter {

    private fragment_Tipus_maquina fragament_tipus;
    public static MainActivity context;

    public adapterTodoListFilterFragmentTipus_Maquina(Context context, int layout, Cursor c, String[] from, int[] to, int flags, fragment_Tipus_maquina fragment_Tipus) {
        super(context, layout, c, from, to, flags);
        fragament_tipus = fragment_Tipus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);

        Cursor linia = (Cursor) getItem(position);

        final long identificador = linia.getInt(linia.getColumnIndexOrThrow(dadesDataSource.TIPUS_ID));
        final String tipus_maquina = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.TIPUS_CODI));

        ImageView delete = view.findViewById(R.id.imDeleteTipus);
        ImageView edit = view.findViewById(R.id.imEditTipus);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragament_tipus.editTipus(tipus_maquina, identificador);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragament_tipus.deleteTipus(identificador, tipus_maquina);
            }
        });

        return view;
    }
}