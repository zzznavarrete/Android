package com.example.lc_xxx.prueba2.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lc_xxx.prueba2.Modelo.Cl_Conexion;
import com.example.lc_xxx.prueba2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_Listar extends Fragment {

Cl_Conexion conexion;
    public F_Listar() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        cargarDatos();

        super.onActivityCreated(savedInstanceState);
    }

    private void cargarDatos() {
        //Nos conectamos a la bd
        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);
        //Indicamos que conexión ejecutar
        SQLiteDatabase database = conexion.getReadableDatabase();
        if(database != null){
            //Indicamos cual es el query que quiero almacenar
            Cursor cursor = database.rawQuery("SELECT * FROM MASCOTAS", null);
            int cantidad = cursor.getCount();
            //Asignamos el largo que va a tener
            String[] arregloMascotas = new String[cantidad];
            int i =0;
            //Nos situamos en la primera fila
            if(cursor.moveToFirst()){
                //Recorrer el cursor mientras tenga datos
                do {

                    String datos = "Nombre:"+ cursor.getString(1) + "                       Raza: " + cursor.getString(2)+ "\n"+" Tipo: "+cursor.getString(3)+"                            Edad:"+cursor.getInt(4);
                    arregloMascotas[i] = datos;
                    i++;
                }while (cursor.moveToNext());  //que se recorra mientras tenga datos

            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arregloMascotas);
            ListView listaMascotas = (ListView) getView().findViewById(R.id.listaMascotas);
            listaMascotas.setAdapter(arrayAdapter);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__listar, container, false);
    }

}
