package com.example.nicomscr.betaprueba3.Fragments.Medicamento;


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

import com.example.nicomscr.betaprueba3.Conexion.Cl_Conexion;
import com.example.nicomscr.betaprueba3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_ListaM extends Fragment {


    public F_ListaM() {
        // Required empty public constructor
    }
    Cl_Conexion conexion;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cargarDatos();


    }


    private void cargarDatos() {
        //Nos conectamos a la bd
        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);
        //Indicamos que conexión ejecutar
        SQLiteDatabase database = conexion.getReadableDatabase();
        if(database != null){
            //Indicamos cual es el query que quiero almacenar
            Cursor cursor = database.rawQuery("SELECT * FROM MEDICAMENTOS", null);
            int cantidad = cursor.getCount();
            //Asignamos el largo que va a tener
            String[] arregloMedicamentos = new String[cantidad];
            int i =0;
            //Nos situamos en la primera fila
            if(cursor.moveToFirst()){
                //Recorrer el cursor mientras tenga datos
                do {

                    String datos = "Nombre: "+ cursor.getString(1) + "             Laboratorio: " + cursor.getString(2)+ "\n"+" Miligramos: "+cursor.getString(3);
                    arregloMedicamentos[i] = datos;
                    i++;
                }while (cursor.moveToNext());  //que se recorra mientras tenga datos

            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arregloMedicamentos);
            ListView listaMedicamentos = (ListView) getView().findViewById(R.id.listaMedicamentos);
            listaMedicamentos.setAdapter(arrayAdapter);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__lista_m, container, false);
    }

}
