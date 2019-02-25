package com.example.nicomscr.betaprueba3.Fragments.Medicamento;


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

import com.example.nicomscr.betaprueba3.Conexion.Cl_Conexion;
import com.example.nicomscr.betaprueba3.Modelo.Cl_Medicamento;
import com.example.nicomscr.betaprueba3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_Eliminar extends Fragment {



    Spinner cboEliminar;
    //Array para representar la información en el spinner
    ArrayList<String> listaMedicamentoSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Medicamento> medicamentos;
    //Conexión
    Cl_Conexion conexion;

    Button btnEliminar;


    public F_Eliminar() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        cboEliminar = (Spinner) getView().findViewById(R.id.cboEliminarM);
        btnEliminar = (Button) getView().findViewById(R.id.btnEliminarM);

        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);

        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaMedicamentoSpinner);
        cboEliminar.setAdapter(arrayAdapter);


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = conexion.getReadableDatabase();
                String[]parametros = {cboEliminar.getSelectedItem().toString()};

                try {
                    long i = db.delete("medicamentos", "nombre = ?", parametros);
                    if(i >0){
                        Toast.makeText(getActivity(), "Medicamento eliminado!", Toast.LENGTH_SHORT).show();
                        cboEliminar.setSelection(0);
                        //Para "refrescar" el fragment y que se refresque la busqueda en la BD osino siguen saliendo los registros eliminados.
                        FragmentManager f = getActivity().getSupportFragmentManager();
                        f.beginTransaction().replace(R.id.content_menu_medicamento, new F_Eliminar()).commit();
                    }else{
                        Toast.makeText(getActivity(), "Selección inválida ", Toast.LENGTH_SHORT).show();

                    }


                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                db.close();

            }
        });

    }

    private void cargarDatos() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cl_Medicamento medicamento = null;
        medicamentos = new ArrayList<Cl_Medicamento>();

        //Generamos la QUERY
        Cursor cursor = db.rawQuery("SELECT nombre FROM medicamentos", null);
        //Recorremos el cursor y lo almacenamos en el arraylist alumnos
        while(cursor.moveToNext()){
            medicamento = new Cl_Medicamento(cursor.getString(0));
            medicamentos.add(medicamento);
        }

        obtenerNombreSpinner();
    }

    private void obtenerNombreSpinner() {
        listaMedicamentoSpinner = new ArrayList<String>();
        listaMedicamentoSpinner.add("Seleccione");

        for(int i =0; i < medicamentos.size(); i++){
            listaMedicamentoSpinner.add(medicamentos.get(i).getNombre());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__eliminar, container, false);
    }

}
