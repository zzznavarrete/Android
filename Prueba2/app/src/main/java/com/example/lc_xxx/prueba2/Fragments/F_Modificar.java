package com.example.lc_xxx.prueba2.Fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lc_xxx.prueba2.Modelo.Cl_Conexion;
import com.example.lc_xxx.prueba2.Modelo.Cl_Mascota;
import com.example.lc_xxx.prueba2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_Modificar extends Fragment {


    //Declaro
    EditText txtNombreMod, txtTipoMod, txtEdadMod, txtRazaMod;
    Spinner cboNombre;
    Button btnBuscar, btnModificar, btnCancelar;
    //Array para representar la información en el spinner
    ArrayList<String> listaMascotaSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Mascota> mascotas;
    //Conexión
    Cl_Conexion conexion;


    public F_Modificar() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        txtNombreMod = (EditText) getView().findViewById(R.id.txtNombreMod);
        txtEdadMod = (EditText) getView().findViewById(R.id.txtEdadMod);
        txtTipoMod = (EditText) getView().findViewById(R.id.txtTipoMod);
        txtRazaMod = (EditText) getView().findViewById(R.id.txtRazaMod);
        cboNombre = (Spinner) getView().findViewById(R.id.cboNombre);
        btnBuscar = (Button) getView().findViewById(R.id.btnBuscar);
        btnModificar = (Button) getView().findViewById(R.id.btnModificar);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelar);

        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);
        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaMascotaSpinner);
        cboNombre.setAdapter(arrayAdapter);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cboNombre.setSelection(0);
                txtEdadMod.setText("");
                txtTipoMod.setText("");
                txtNombreMod.setText("");
                txtRazaMod.setText("");

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cboNombre.getSelectedItem().toString().equals("Seleccione")){
                    Toast.makeText(getActivity(), "El nombre no es válido", Toast.LENGTH_SHORT).show();
                }else{
                    consultarMascota();
                }
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarMascota();
            }
        });





        super.onActivityCreated(savedInstanceState);
    }

    private void modificarMascota() {

        if(txtNombreMod.getText().toString().length()>0 && txtEdadMod.getText().toString().length()>0 && txtTipoMod.getText().toString().length()>0 && txtRazaMod.getText().toString().length()>0){

        int edad  = Integer.parseInt(txtEdadMod.getText().toString());

        if(edad >0){
            Toast.makeText(getActivity(), "La edad es mayor a 0", Toast.LENGTH_SHORT).show();


        SQLiteDatabase db = conexion.getReadableDatabase();
        String[]parametros = {cboNombre.getSelectedItem().toString()};

        try {
            ContentValues campos = new ContentValues();
            campos.put("nombre",txtNombreMod.getText().toString());
            campos.put("tipo",txtTipoMod.getText().toString());
            campos.put("raza", txtRazaMod.getText().toString());
            campos.put("edad", txtEdadMod.getText().toString());
            int q = db.update("mascotas", campos, "nombre = ?", parametros);
            if(q >0){
                Toast.makeText(getActivity(), "Mascota modificada", Toast.LENGTH_SHORT).show();
                limpiar();
            }else{
                Toast.makeText(getActivity(), "No se pudo modificar Mascota", Toast.LENGTH_SHORT).show();
            }


            db.close();


        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            limpiar();
        }
        }else{
            Toast.makeText(getActivity(), "La edad debe ser mayor a 0 ", Toast.LENGTH_SHORT).show();
        }

        }else{
            Toast.makeText(getActivity(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }


    private void limpiar() {

        cboNombre.setSelection(0);
        //Para "refrescar" el fragment y que se refresque la busqueda en la BD osino siguen saliendo los registros eliminados.
        FragmentManager f = getActivity().getSupportFragmentManager();
        f.beginTransaction().replace(R.id.content_main, new F_Modificar()).commit();
    }

    private void consultarMascota() {
        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[]parametros = {cboNombre.getSelectedItem().toString()};

        try {
            //Almacenamos el resultado del select
            Cursor cursor = db.rawQuery("SELECT nombre,tipo, raza, edad FROM mascotas WHERE nombre = ?", parametros);
            cursor.moveToFirst();
            //Asignamos os resultados a los EditText
            txtNombreMod.setText(cursor.getString(0));
            txtTipoMod.setText(cursor.getString(1));
            txtRazaMod.setText(cursor.getString(2));
            txtEdadMod.setText(cursor.getString(3));
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cl_Mascota mascota = null;
        mascotas = new ArrayList<Cl_Mascota>();

        //Generamos la QUERY
        Cursor cursor = db.rawQuery("SELECT nombre FROM mascotas", null);
        //Recorremos el cursor y lo almacenamos en el arraylist alumnos
        while(cursor.moveToNext()){
            mascota = new Cl_Mascota(cursor.getString(0));
            mascotas.add(mascota);
        }

        obtenerNombreSpinner();
    }

    private void obtenerNombreSpinner() {
        listaMascotaSpinner = new ArrayList<String>();
        listaMascotaSpinner.add("Seleccione");

        //leemos la lista generada enm alumnos y la cargamos en la lista para usarla en el spinner
        for(int i =0; i < mascotas.size(); i++){
            listaMascotaSpinner.add(mascotas.get(i).getNombre());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__modificar, container, false);
    }

}
