package com.example.buidemapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class fragment_Maquinas extends Fragment {

    public static MainActivity context;
    private static dadesDataSource bd;
    private adapterTodoListFilterFragmentMaquinas scTasks;
    private View view;
    private int id_general_tipus;
    private int id_general_zonas;

    private static String[] from = new String[]{bd.MAQUINA_NOM_CLIENT,bd.MAQUINA_ADRECA, bd.MAQUINA_CODI_POSTAL, bd.MAQUINA_POBLACIO, bd.MAQUINA_NUM_SERIE_MAQUINA,bd.MAQUINA_DATA_REVISIO, bd.MAQUINA_TIPUS, bd.MAQUINA_ZONA};
    private static int[] to = new int[]{R.id.lblNomClient, R.id.lblAdreca, R.id.lblCodiPostal, R.id.lblPoblacio, R.id.lblNumeroSerie, R.id.lblDataRevisio, R.id.spinner_Maquinas,  R.id.spinner_Zonas};

    public fragment_Maquinas() {
        // Required empty public constructor
    }

  /*  */



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maquina, container, false);
        cargarMaquinas(view);
        ImageView add = view.findViewById(R.id.imAddMaquina);
        ImageView search = view.findViewById(R.id.imBuscar);
        ImageView ordenar = view.findViewById(R.id.imOrdenar);
        ImageView imtodas = view.findViewById(R.id.imTotesMaquines);

        imtodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totesMaquines();
                Snackbar.make(getView(), "Mostrant totes les maquines disponibles", Snackbar.LENGTH_LONG).show();

            }
        });

        ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View regisText = factory.inflate(R.layout.layout_ordenar_maquinas, null);
                builder.setTitle("Ordenar elements");

                builder.setView(regisText)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText atributo = regisText.findViewById(R.id.edtOrdenarAtributo);
                                int numeroAtributo = Integer.valueOf(atributo.getText().toString());

                                if (numeroAtributo > 4 || numeroAtributo < 0 ){
                                    Snackbar.make(getView(), "Escull una opcio correcte", Snackbar.LENGTH_LONG).show();
                                }
                                else {

                                    if (numeroAtributo == 0){
                                        Snackbar.make(getView(), "S'han ordenat les maquines per el nom del client", Snackbar.LENGTH_LONG).show();

                                    }
                                    else if (numeroAtributo == 1){
                                        Snackbar.make(getView(), "S'han ordenat les maquines per la Zona en la que es troben", Snackbar.LENGTH_LONG).show();

                                    }
                                    else if (numeroAtributo == 2){
                                        Snackbar.make(getView(), "S'han ordenat les maquines per la població", Snackbar.LENGTH_LONG).show();

                                    }
                                    else if (numeroAtributo == 3){
                                        Snackbar.make(getView(), "S'han ordenat les maquines per la adreça del client", Snackbar.LENGTH_LONG).show();

                                    }
                                    else if (numeroAtributo == 4){
                                        Snackbar.make(getView(), "S'ha ordenat les maquines per la data de la ultima revisio", Snackbar.LENGTH_LONG).show();

                                    }

                                    Cursor cursorArticles = bd.ordenarMaquinas(numeroAtributo);
                                    scTasks.changeCursor(cursorArticles);
                                    scTasks.notifyDataSetChanged();

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

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog ad;

                ad = new AlertDialog.Builder(getActivity()).create();
                ad.setTitle("Buscar Elements");
                ad.setMessage("Introdueix el numero de serie de la maquina que desitgi buscar. Nomes el codi numeric");


                final EditText edtValor = new EditText(getContext());
                edtValor.setInputType(InputType.TYPE_CLASS_NUMBER);
                ad.setView(edtValor);

                ad.setButton(AlertDialog.BUTTON_POSITIVE,"Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (edtValor.length() > 4) {
                            Snackbar.make(getView(), "El id ha de ser de 4 caracters", Snackbar.LENGTH_LONG).show();
                        }

                        else {
                            String id = edtValor.getText().toString();

                            id = "MQ" + id;

                            Cursor cursorArticles = bd.buscarMaquinas(id);

                                scTasks.changeCursor(cursorArticles);
                                scTasks.notifyDataSetChanged();
                        }
                    }
                });

                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // no fem res.
                    }
                });
                ad.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Añadir nueva maquina");

                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View regisText = factory.inflate(R.layout.layout_maquina, null);

                Spinner desplegable_tipus = regisText.findViewById(R.id.spinner_Maquinas);
                Spinner desplegable_zonas = regisText.findViewById(R.id.spinner_Zonas);

                loadSpinnerData(0,desplegable_tipus);
                loadSpinnerData(1,desplegable_zonas);

                desplegable_tipus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        id_general_tipus = position + 1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Snackbar.make(getView(), "Selecciona un tipus sisplau", Snackbar.LENGTH_LONG).show();
                    }
                });

                desplegable_zonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        id_general_zonas = position + 1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Snackbar.make(getView(), "Selecciona una zona sisplau", Snackbar.LENGTH_LONG).show();
                    }
                });

                builder.setView(regisText)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText nomClient = regisText.findViewById(R.id.edtNomClient);
                                EditText adreca = regisText.findViewById(R.id.edtAdreca);
                                EditText codiPostal = regisText.findViewById(R.id.edtCodiPostal);
                                EditText Poblacion = regisText.findViewById(R.id.edtPoblacio);
                                EditText telefon = regisText.findViewById(R.id.edtTelefon);
                                EditText email = regisText.findViewById(R.id.edtEmail);
                                EditText numSerie = regisText.findViewById(R.id.edtNumSerie);
                                EditText ultimaRevisio = regisText.findViewById(R.id.edtRevisioFecha);

                                String nomClientString = nomClient.getText().toString();
                                String adrecaString = adreca.getText().toString();
                                String codiPostalString = codiPostal.getText().toString();
                                String poblacioString = Poblacion.getText().toString();
                                String telefonString = telefon.getText().toString();
                                String emailString = email.getText().toString();
                                String numSerieString = numSerie.getText().toString();
                                String ultimaRevisioString = ultimaRevisio.getText().toString();
                                String id_zona = String.valueOf(id_general_zonas) ;
                                String id_tipus = String.valueOf(id_general_tipus);

                                adrecaString = "c/" + adrecaString;

                                Cursor c = bd.MaquinaRepetida(numSerieString);
                                if (c.getCount() > 0){
                                    Snackbar.make(getView(), "El numero de serie de la maquina que intenteu introduir ja existeix", Snackbar.LENGTH_LONG).show();
                                }

                                else if (numSerieString.length() > 4){
                                    Snackbar.make(getView(), "El numero de serie ha de ser nomes de 4 digits", Snackbar.LENGTH_LONG).show();
                                }
                                else {
                                    numSerieString = "MQ" + numSerieString;
                                }

                                if (codiPostalString.length() > 5){
                                    Snackbar.make(getView(), "El codi postal ha de tindre 5 digits", Snackbar.LENGTH_LONG).show();
                                }

                                else if (nomClientString.equals("") || adrecaString.equals("") || codiPostalString.equals("") || poblacioString.equals("") || numSerieString.equals("")){
                                    Snackbar.make(getView(), "Sisplau, introdueixi els camps obligatoris", Snackbar.LENGTH_LONG).show();
                                }
                                else{
                                    bd.addMaquina(nomClientString,adrecaString,codiPostalString,poblacioString,telefonString,emailString,numSerieString,ultimaRevisioString, id_tipus, id_zona);
                                    cargarMaquinas(view);
                                    Toast.makeText(getContext(),"S'ha afegit la maquina amb numero de serie: " + numSerie,Toast.LENGTH_SHORT).show();

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



    private void loadSpinnerData(int identificador, Spinner spinner) {
        // database handler
        List<String> lables = null;

        // Spinner Drop down elements
        if (identificador == 0){
            lables = bd.spinnerTipus();
        }
        else if (identificador == 1){
            lables = bd.spinnerZones();
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void cargarMaquinas(View view){
        bd = new dadesDataSource(getActivity());
        Cursor cursorTipus = bd.totes_maquines();
        scTasks = new adapterTodoListFilterFragmentMaquinas(getActivity(), R.layout.maquina, cursorTipus, from, to, 1, fragment_Maquinas.this);
        ListView lvZonas = view.findViewById(R.id.lista_Maquinas);
        lvZonas.setAdapter(scTasks);
    }

    public void totesMaquines(){
        Cursor cursorArticles = bd.totes_maquines();
        scTasks.changeCursor(cursorArticles);
        scTasks.notifyDataSetChanged();
    }

    public void editMaquina(long identificador, String adreca, String codipostal, String datarevisio, String email, String nomclient, final String num_serie, String poblacio, String telefon, int tipus, int zonas){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Editar maquina");

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View regisText = factory.inflate(R.layout.layout_maquina, null);

        Spinner desplegable_tipus = regisText.findViewById(R.id.spinner_Maquinas);
        Spinner desplegable_zonas = regisText.findViewById(R.id.spinner_Zonas);

        loadSpinnerData(0,desplegable_tipus);
        loadSpinnerData(1,desplegable_zonas);





        final EditText edt_nomClient = regisText.findViewById(R.id.edtNomClient);
        final EditText edt_adreca = regisText.findViewById(R.id.edtAdreca);
        final EditText edt_codiPostal = regisText.findViewById(R.id.edtCodiPostal);
        final EditText edt_Poblacion = regisText.findViewById(R.id.edtPoblacio);
        final EditText edt_telefon = regisText.findViewById(R.id.edtTelefon);
        final EditText edt_email = regisText.findViewById(R.id.edtEmail);
        final EditText edt_numSerie = regisText.findViewById(R.id.edtNumSerie);
        final EditText edt_ultimaRevisio = regisText.findViewById(R.id.edtRevisioFecha);

        edt_nomClient.setText(nomclient);
        edt_adreca.setText(adreca);
        edt_codiPostal.setText(codipostal);
        edt_Poblacion.setText(poblacio);
        edt_telefon.setText(telefon);
        edt_email.setText(email);
        edt_numSerie.setText(num_serie);
        edt_ultimaRevisio.setText(datarevisio);
       // desplegable_tipus.setSelection();
       // desplegable_tipus.setTag(tipus);
       // desplegable_zonas.setTag(zonas);
        desplegable_tipus.setSelection(tipus);
        desplegable_zonas.setSelection(zonas);

        desplegable_tipus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_general_tipus = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(getView(), "Selecciona un tipus sisplau", Snackbar.LENGTH_LONG).show();
            }
        });

        desplegable_zonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_general_zonas = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(getView(), "Selecciona una zona sisplau", Snackbar.LENGTH_LONG).show();
            }
        });

        builder.setView(regisText)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        String nomClientString = edt_nomClient.getText().toString();
                        String adrecaString = edt_adreca.getText().toString();
                        String codiPostalString = edt_codiPostal.getText().toString();
                        String poblacioString = edt_Poblacion.getText().toString();
                        String telefonString = edt_telefon.getText().toString();
                        String emailString = edt_email.getText().toString();
                        String numSerieString = edt_numSerie.getText().toString();
                        String ultimaRevisioString = edt_ultimaRevisio.getText().toString();
                        String id_zona = String.valueOf(id_general_zonas);
                        String id_tipus = String.valueOf(id_general_tipus);

                        adrecaString = "c/" + adrecaString;



                        Cursor c = bd.MaquinaRepetida(numSerieString);

                        if (c.getCount() == 1){
                            if (numSerieString.length() > 4){
                                Snackbar.make(getView(), "El numero de serie ha de ser nomes de 4 digits", Snackbar.LENGTH_LONG).show();

                            }

                            else if (codiPostalString.length() > 5){
                                Snackbar.make(getView(), "El codi postal ha de tindre 5 digits", Snackbar.LENGTH_LONG).show();
                            }

                            else if (nomClientString.equals("") || adrecaString.equals("") || codiPostalString.equals("") || poblacioString.equals("") || numSerieString.equals("")){
                                Snackbar.make(getView(), "Sisplau, introdueixi els camps obligatoris", Snackbar.LENGTH_LONG).show();
                            }
                            else{
                                numSerieString = "MQ" + numSerieString;
                                bd.addMaquina(nomClientString,adrecaString,codiPostalString,poblacioString,telefonString,emailString,numSerieString,ultimaRevisioString, id_tipus, id_zona);
                                cargarMaquinas(view);
                                Toast.makeText(getContext(),"S'ha afegit la maquina amb numero de serie; " + num_serie ,Toast.LENGTH_SHORT).show();

                            }
                        }
                        else if (c.getCount() > 1 ){
                            Snackbar.make(getView(), "El numero de serie de la maquina que intenteu introduir ja existeix", Snackbar.LENGTH_LONG).show();
                        }




                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();

    }

    public void deleteMaquina(final long identificador, final String num_serie){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Eliminar la maquina?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bd.eliminarMaquina(identificador);
                Snackbar.make(getView(), "S'ha eliminat la maquina amb numero de serie: " + num_serie, Snackbar.LENGTH_LONG).show();
                totesMaquines();
            }
        });

        builder.setNegativeButton("No", null);
        builder.show();
    }

}

class adapterTodoListFilterFragmentMaquinas extends android.widget.SimpleCursorAdapter {

    private fragment_Maquinas fragment_maquinas;
    public static MainActivity context;

    public adapterTodoListFilterFragmentMaquinas(Context context, int layout, Cursor c, String[] from, int[] to, int flags, fragment_Maquinas fragment) {
        super(context, layout, c, from, to, flags);
        fragment_maquinas = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        Cursor linia = (Cursor) getItem(position);

        final long identificador = linia.getInt(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_ID));
        final String maquina_adreca = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_ADRECA));
        final String maquina_codipostal = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_CODI_POSTAL));
        final String maquina_datarevisio = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_DATA_REVISIO));
        final String maquina_email = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_EMAIL));
        final String maquina_nomclient = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_NOM_CLIENT));
        final String maquina_num_serie = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_NUM_SERIE_MAQUINA));
        final String maquina_poblacio = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_POBLACIO));
        final String maquina_telefon = linia.getString(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_TELEFON));
        final int maquina_desplegable_tipus = linia.getInt(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_TIPUS));
        final int maquina_desplegable_zonas = linia.getInt(linia.getColumnIndexOrThrow(dadesDataSource.MAQUINA_ZONA));

        ImageView delete = view.findViewById(R.id.imDeleteMaquina);
        ImageView edit = view.findViewById(R.id.imEditMaquina);
        ImageView telefono = view.findViewById(R.id.imTelefon);
        ImageView correo = view.findViewById(R.id.imEmail);
        ImageView maps = view.findViewById(R.id.imMaps);

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + maquina_telefon);
                Intent call = new Intent(Intent.ACTION_DIAL, number);
                fragment_maquinas.startActivity(call);
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent correu = new Intent(Intent.ACTION_SEND);

                correu.setType("text/plain");
                correu.putExtra(Intent.EXTRA_SUBJECT, "Propera revisió màquina nº" + maquina_num_serie);
                correu.putExtra(Intent.EXTRA_EMAIL, new String[]{maquina_email});

                fragment_maquinas.startActivity(correu);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_maquinas.editMaquina(identificador, maquina_adreca, maquina_codipostal, maquina_datarevisio, maquina_email,maquina_nomclient, maquina_num_serie, maquina_poblacio, maquina_telefon, maquina_desplegable_tipus, maquina_desplegable_zonas);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_maquinas.deleteMaquina(identificador, maquina_num_serie);
            }
        });


        return view;
    }
}
