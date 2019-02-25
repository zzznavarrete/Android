package com.example.nicomscr.betaprueba3.Fragments.Paciente;


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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicomscr.betaprueba3.Conexion.Cl_Conexion;
import com.example.nicomscr.betaprueba3.Modelo.Cl_Paciente;
import com.example.nicomscr.betaprueba3.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_Historial extends Fragment {

    TextView txtID, txtRut, txtNombre, txtApellido, txtMedicamento, txtDireccion, txtEdad;
    Button btnBuscar;
    //Array para representar la información en el spinner
    ArrayList<String> listaPacienteSpinner;
    //Arraylist que contiene los datos
    ArrayList<Cl_Paciente> pacientes;

    Spinner cboRutH ;

    //Conexión
    Cl_Conexion conexion;

    public F_Historial() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtID = (TextView) getView().findViewById(R.id.txtIdH);
        txtRut = (TextView) getView().findViewById(R.id.txtRutH);
        txtNombre = (TextView) getView().findViewById(R.id.txtNombreH);
        txtApellido = (TextView) getView().findViewById(R.id.txtApellidoH);
        txtMedicamento = (TextView) getView().findViewById(R.id.txtMedicamentoH);
        txtDireccion = (TextView) getView().findViewById(R.id.txtDireH);
        txtEdad = (TextView) getView().findViewById(R.id.txtEdadH);

        btnBuscar = (Button) getView().findViewById(R.id.btnBuscarH);

        cboRutH = (Spinner) getView().findViewById(R.id.cboRutH);

        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);

        cargarDatos();

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listaPacienteSpinner);
        cboRutH.setAdapter(arrayAdapter);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cboRutH.getSelectedItem().toString().equals("Seleccione")){
                    Toast.makeText(getActivity(), "El rut no es válido", Toast.LENGTH_SHORT).show();
                }else{
                    consultarPaciente();
                }
            }
        });

    }
    private void cargarDatos() {
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
        String[]parametros = {cboRutH.getSelectedItem().toString()};

        try {
            //Almacenamos el resultado del select
            Cursor cursor = db.rawQuery("SELECT * FROM pacientes WHERE rut = ?", parametros);
            cursor.moveToFirst();
            //Asignamos os resultados a los EditText
            txtID.setText(cursor.getString(0));
            txtRut.setText(cursor.getString(1));
            txtNombre.setText(cursor.getString(2));
            txtApellido.setText(cursor.getString(3));
            txtEdad.setText(cursor.getString(4));
            txtDireccion.setText(cursor.getString(5));
            txtMedicamento.setText(cursor.getString(6));
        }catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__historial, container, false);
    }

}
