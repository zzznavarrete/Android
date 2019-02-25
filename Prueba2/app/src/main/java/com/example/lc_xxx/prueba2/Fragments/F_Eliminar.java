package com.example.lc_xxx.prueba2.Fragments;


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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lc_xxx.prueba2.Modelo.Cl_Conexion;
import com.example.lc_xxx.prueba2.Modelo.Cl_Mascota;
import com.example.lc_xxx.prueba2.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_Eliminar extends Fragment {

    Spinner cboEliminar;
    //Array para representar la información en el spinner
    ArrayList<String> listaMascotaSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Mascota> mascotas;
    //Conexión
    Cl_Conexion conexion;

    Button btnEliminar;

    public F_Eliminar() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        cboEliminar = (Spinner) getView().findViewById(R.id.cboEliminar);
        btnEliminar = (Button) getView().findViewById(R.id.btnEliminar);

        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);

        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaMascotaSpinner);
        cboEliminar.setAdapter(arrayAdapter);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = conexion.getReadableDatabase();
                String[]parametros = {cboEliminar.getSelectedItem().toString()};

                try {
                    long i = db.delete("mascotas", "nombre = ?", parametros);
                    if(i >0){
                        Toast.makeText(getActivity(), "Mascota eliminada!", Toast.LENGTH_SHORT).show();
                        cboEliminar.setSelection(0);
                        //Para "refrescar" el fragment y que se refresque la busqueda en la BD osino siguen saliendo los registros eliminados.
                        FragmentManager f = getActivity().getSupportFragmentManager();
                        f.beginTransaction().replace(R.id.content_main, new F_Eliminar()).commit();
                    }else{
                        Toast.makeText(getActivity(), "Selección inválida ", Toast.LENGTH_SHORT).show();

                    }


                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                db.close();

            }
        });


        super.onActivityCreated(savedInstanceState);
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
        return inflater.inflate(R.layout.fragment_f__eliminar, container, false);
    }

}
