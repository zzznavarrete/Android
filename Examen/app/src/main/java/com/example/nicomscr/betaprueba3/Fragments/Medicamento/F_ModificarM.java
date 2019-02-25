package com.example.nicomscr.betaprueba3.Fragments.Medicamento;


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

import com.example.nicomscr.betaprueba3.Conexion.Cl_Conexion;
import com.example.nicomscr.betaprueba3.Modelo.Cl_Medicamento;
import com.example.nicomscr.betaprueba3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_ModificarM extends Fragment {

    //Declaro
    EditText txtNombreMod, txtMiliMod;
    Spinner cboNombre, cboLabMod;
    Button btnBuscar, btnModificar, btnCancelar;
    //Array para representar la información en el spinner
    ArrayList<String> listaMedicamentoSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Medicamento> medicamentos;
    //Conexión
    Cl_Conexion conexion;
    String[] tipos = {"Seleccione","Lab. Chile", "Walter White", "Lab. Knop", "BNI"};

    public F_ModificarM() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        txtNombreMod = (EditText) getView().findViewById(R.id.txtNombreMMod);
        txtMiliMod = (EditText) getView().findViewById(R.id.txtMiligramosMod);
        cboLabMod = (Spinner) getView().findViewById(R.id.cboLabMod);
        cboNombre = (Spinner) getView().findViewById(R.id.cboNombreM);
        btnBuscar = (Button) getView().findViewById(R.id.btnBuscar);
        btnModificar = (Button) getView().findViewById(R.id.btnModificar);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelar);

        //Debe estar antes de cargar el spinner que lée de la BD
        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);
        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, tipos);
        cboLabMod.setAdapter(arrayAdapter);

        ArrayAdapter<CharSequence> arrayAdapter2 =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaMedicamentoSpinner);
        cboNombre.setAdapter(arrayAdapter2);





        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cboNombre.setSelection(0);
                cboLabMod.setSelection(0);
                txtNombreMod.setText("");
                txtMiliMod.setText("");

            }
        });



        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarMedicamento();
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cboNombre.getSelectedItem().toString().equals("Seleccione")){
                    Toast.makeText(getActivity(), "El nombre no es válido", Toast.LENGTH_SHORT).show();
                }else{
                    consultarMedicamento();
                }
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


    private void consultarMedicamento() {
        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[]parametros = {cboNombre.getSelectedItem().toString()};

        try {
            //Almacenamos el resultado del select
            Cursor cursor = db.rawQuery("SELECT nombre,laboratorio, miligramos FROM medicamentos WHERE nombre = ?", parametros);
            cursor.moveToFirst();
            //Asignamos os resultados a los EditText
            txtNombreMod.setText(cursor.getString(0));

            //Para encontrar el string en el cbo
            for(int i =1; i< tipos.length; i++){
                cboLabMod.setSelection(i);
                if(cursor.getString(1).equals(cboLabMod.getSelectedItem().toString())){
                    break;
                }
            }
            txtMiliMod.setText(cursor.getString(2));

        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void limpiar() {

        cboNombre.setSelection(0);
        //Para "refrescar" el fragment y que se refresque la busqueda en la BD osino siguen saliendo los registros eliminados.
        FragmentManager f = getActivity().getSupportFragmentManager();
        f.beginTransaction().replace(R.id.content_menu_medicamento, new F_ModificarM()).commit();
    }

    private void modificarMedicamento() {

        if(txtNombreMod.getText().toString().length()>0 && txtMiliMod.getText().toString().length()>0 ){

            double mili = Double.parseDouble(txtMiliMod.getText().toString());

            if(mili >0){


                SQLiteDatabase db = conexion.getReadableDatabase();
                String[]parametros = {cboNombre.getSelectedItem().toString()};

                try {
                    ContentValues campos = new ContentValues();
                    campos.put("nombre",txtNombreMod.getText().toString());
                    campos.put("laboratorio",cboLabMod.getSelectedItem().toString());
                    campos.put("miligramos", txtMiliMod.getText().toString());
                    int q = db.update("medicamentos", campos, "nombre = ?", parametros);
                    if(q >0){
                        Toast.makeText(getActivity(), "Medicamento modificado", Toast.LENGTH_SHORT).show();
                        limpiar();
                    }else{
                        Toast.makeText(getActivity(), "No se pudo modificar medicamento", Toast.LENGTH_SHORT).show();
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__modificar_m, container, false);
    }

}
