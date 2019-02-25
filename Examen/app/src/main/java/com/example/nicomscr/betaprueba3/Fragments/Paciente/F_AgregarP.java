package com.example.nicomscr.betaprueba3.Fragments.Paciente;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.nicomscr.betaprueba3.Modelo.Cl_Paciente;
import com.example.nicomscr.betaprueba3.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_AgregarP extends Fragment {

    Spinner cboMedAP;
    //Array para representar la información en el spinner
    ArrayList<String> listaMedicamentoSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Medicamento> medicamentos;


    EditText txtNombreP, txtRutP, txtApellidoP, txtDireccionP, txtEdadP;
    Button btnAgregar, btnCancelar;
    Cl_Conexion conexion;





    public F_AgregarP() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        btnAgregar = (Button) getView().findViewById(R.id.btnAgregarAP);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelarAP);

        cboMedAP = (Spinner) getView().findViewById(R.id.cboMedAP);

        txtNombreP = (EditText) getView().findViewById(R.id.txtNombreP);
        txtRutP = (EditText) getView().findViewById(R.id.txtRutP);
        txtApellidoP = (EditText) getView().findViewById(R.id.txtApellidoP);
        txtDireccionP = (EditText) getView().findViewById(R.id.txtDireccionP);
        txtEdadP = (EditText) getView().findViewById(R.id.txtEdadP);

        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);

        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaMedicamentoSpinner);
        cboMedAP.setAdapter(arrayAdapter);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombreP.getText().toString().length()>0 &&txtRutP.getText().toString().length()>0 &&txtEdadP.getText().toString().length()>0&&txtApellidoP.getText().toString().length()>0&&txtDireccionP.getText().toString().length()>0){
                    if(cboMedAP.getSelectedItem().toString() != "Seleccione") {

                        int edad = Integer.parseInt(txtEdadP.getText().toString());

                        if (edad > 0) {

                            conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);

                            String username = txtRutP.getText().toString();
                            String[] usernameSSC = {username};
                            //Validacion2
                            SQLiteDatabase db = conexion.getReadableDatabase();
                            //Generamos la QUERY
                            //Almacenamos el resultado del select
                            Cursor cursor = db.rawQuery("SELECT * FROM pacientes WHERE rut = ?", usernameSSC);
                            boolean sswitch = true;

                            while (cursor.moveToNext()) {
                                if (cursor.getString(1).equals(username)) {
                                    sswitch = false;
                                }
                            }


                            if (sswitch) {
                                String nombre = txtNombreP.getText().toString();
                                String apellido = txtApellidoP.getText().toString();
                                String rut = txtRutP.getText().toString();
                                String direccion = txtDireccionP.getText().toString();
                                String medicamento = cboMedAP.getSelectedItem().toString();


                                Cl_Paciente paciente1 = new Cl_Paciente(rut, nombre, apellido, direccion, medicamento, edad);


                                SQLiteDatabase database = conexion.getWritableDatabase();

                                if (database != null) {
                                    ContentValues parametros = new ContentValues();
                                    parametros.put("rut", paciente1.getRut());
                                    parametros.put("nombre", paciente1.getNombre());
                                    parametros.put("apellido", paciente1.getApellido());
                                    parametros.put("direccion", paciente1.getDireccion());
                                    parametros.put("medicamento", paciente1.getMedicamento());
                                    parametros.put("edad", paciente1.getEdad());


                                    //Ejecutamos nuestro QUERY
                                    long i = database.insert("pacientes", null, parametros);
                                    if (i > 0) {
                                        txtNombreP.setText("");
                                        txtApellidoP.setText("");
                                        txtRutP.setText("");
                                        txtDireccionP.setText("");
                                        txtEdadP.setText("");
                                        cboMedAP.setSelection(0);
                                        Toast.makeText(getActivity(), "Registro insertado", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getActivity(), "Error al insertar registro", Toast.LENGTH_LONG).show();

                                    }

                                    //Cerramos la db
                                    database.close();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Ya existe una paciente con ese rut", Toast.LENGTH_SHORT).show();

                            }


                        } else {
                            Toast.makeText(getActivity(), "La edad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                            txtEdadP.setError("mayor a 0");

                        }
                    }else{

                        Toast.makeText(getActivity(), "Debe seleccionar un medicamento válido", Toast.LENGTH_SHORT).show();

                    }


                }else{
                    Toast.makeText(getActivity(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                    txtNombreP.setError("");
                    txtEdadP.setError("");
                    txtDireccionP.setError("");
                    txtApellidoP.setError("");
                    txtRutP.setError("");
                }




            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNombreP.setText("");
                cboMedAP.setSelection(0);
                txtEdadP.setText("");
                txtDireccionP.setText("");
                txtRutP.setText("");
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
        return inflater.inflate(R.layout.fragment_f__agregar, container, false);
    }

}
