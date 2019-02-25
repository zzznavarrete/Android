package com.example.nicomscr.betaprueba3.Fragments.Paciente;


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
import com.example.nicomscr.betaprueba3.Fragments.Medicamento.F_ModificarM;
import com.example.nicomscr.betaprueba3.Modelo.Cl_Medicamento;
import com.example.nicomscr.betaprueba3.Modelo.Cl_Paciente;
import com.example.nicomscr.betaprueba3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_ModificarP extends Fragment {

    //Declaro
    EditText txtNombreMP, txtApellidoMP, txtDireccionMP, txtEdadMP;
    Button btnBuscar, btnModificar, btnCancelar;
    //Array para representar la información en el spinner
    ArrayList<String> listaPacienteSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Paciente> pacientes;

    Spinner cboRut, cboMedMP;


    //Array para representar la información en el spinner
    ArrayList<String> listaMedicamentoSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Medicamento> medicamentos;


    //Conexión
    Cl_Conexion conexion;

    public F_ModificarP() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtNombreMP = (EditText) getView().findViewById(R.id.txtNombreMP);
        txtApellidoMP = (EditText) getView().findViewById(R.id.txtApellidoMP);
        txtDireccionMP = (EditText) getView().findViewById(R.id.txtDireccionMP);
        txtEdadMP = (EditText) getView().findViewById(R.id.txtEdadMP);


        btnBuscar = (Button) getView().findViewById(R.id.btnBuscarMP);
        btnModificar = (Button) getView().findViewById(R.id.btnModificarMP);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelarMP);



        cboMedMP = (Spinner) getView().findViewById(R.id.cboMedMP);
        cboRut = (Spinner) getView().findViewById(R.id.cboRutMP);

        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);

        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaMedicamentoSpinner);
        cboMedMP.setAdapter(arrayAdapter);

        cargarDatos2();
        ArrayAdapter<CharSequence> arrayAdapter2 =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaPacienteSpinner);
        cboRut.setAdapter(arrayAdapter2);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cboRut.setSelection(0);
                cboMedMP.setSelection(0);
                txtNombreMP.setText("");
                txtApellidoMP.setText("");
                txtDireccionMP.setText("");
                txtEdadMP.setText("");

            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarPaciente();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cboRut.getSelectedItem().toString().equals("Seleccione")){
                    Toast.makeText(getActivity(), "El rut no es válido", Toast.LENGTH_SHORT).show();
                }else{
                    consultarPaciente();
                }
            }
        });



    }

    private void cargarDatos2() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cl_Paciente paciente = null;
        pacientes = new ArrayList<Cl_Paciente>();

        //Generamos la QUERY
        Cursor cursor = db.rawQuery("SELECT rut FROM pacientes", null);
        //Recorremos el cursor y lo almacenamos en el arraylist alumnos
        while(cursor.moveToNext()){
            paciente = new Cl_Paciente(cursor.getString(0));
            pacientes.add(paciente);
        }

        obtenerRutSpinner();
    }

    private void obtenerRutSpinner() {
        listaPacienteSpinner = new ArrayList<String>();
        listaPacienteSpinner.add("Seleccione");

        for(int i =0; i < pacientes.size(); i++){
            listaPacienteSpinner.add(pacientes.get(i).getRut());

        }

    }

    private void consultarPaciente() {
        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[]parametros = {cboRut.getSelectedItem().toString()};

        try {
            //Almacenamos el resultado del select
            Cursor cursor = db.rawQuery("SELECT nombre, apellido, direccion, medicamento, edad FROM pacientes WHERE rut = ?", parametros);
            cursor.moveToFirst();
            //Asignamos os resultados a los EditText
            txtNombreMP.setText(cursor.getString(0));
            txtApellidoMP.setText(cursor.getString(1));
            txtDireccionMP.setText(cursor.getString(2));

            //Para encontrar el string en el cbo
            for(int i =0; i< listaMedicamentoSpinner.size(); i++){
                cboMedMP.setSelection(i);
                if(cursor.getString(3).equals(cboMedMP.getSelectedItem().toString())){
                    break;
                }
            }


            txtEdadMP.setText(cursor.getString(4));




        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void modificarPaciente() {
        if(txtEdadMP.getText().toString().length()> 0&& txtNombreMP.getText().toString().length()>0 &&txtEdadMP.getText().toString().length()>0&&txtApellidoMP.getText().toString().length()>0&&txtDireccionMP.getText().toString().length()>0 ){

            int edad = Integer.parseInt(txtEdadMP.getText().toString());

            if(edad >0){


                SQLiteDatabase db = conexion.getReadableDatabase();
                String[]parametros = {cboRut.getSelectedItem().toString()};

                try {
                    ContentValues campos = new ContentValues();
                    campos.put("nombre",txtNombreMP.getText().toString());
                    campos.put("apellido",txtApellidoMP.getText().toString());
                    campos.put("direccion",txtDireccionMP.getText().toString());
                    campos.put("medicamento",cboMedMP.getSelectedItem().toString());
                    campos.put("edad", txtEdadMP.getText().toString());
                    int q = db.update("pacientes", campos, "rut = ?", parametros);
                    if(q >0){
                        Toast.makeText(getActivity(), "Paciente modificado", Toast.LENGTH_SHORT).show();
                        limpiar();
                    }else{
                        Toast.makeText(getActivity(), "No se pudo modificar paciente", Toast.LENGTH_SHORT).show();
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

        cboRut.setSelection(0);
        //Para "refrescar" el fragment y que se refresque la busqueda en la BD osino siguen saliendo los registros eliminados.
        FragmentManager f = getActivity().getSupportFragmentManager();
        f.beginTransaction().replace(R.id.content_main, new F_ModificarP()).commit();

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
        return inflater.inflate(R.layout.fragment_f__modificar, container, false);
    }

}
