package com.example.nicomscr.betaprueba3.Fragments.Medicamento;


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
import com.example.nicomscr.betaprueba3.R;

/**
 * A simple {@link Fragment} subclass.
 */



public class F_AgregarM extends Fragment {

    EditText txtNombre, txtMiligramos;
    Button btnAgregar, btnCancelar;
    Spinner cboLab;
    Cl_Conexion conexion;



    public F_AgregarM() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        txtNombre = (EditText) getView().findViewById(R.id.txtNombreM);
        txtMiligramos = (EditText) getView().findViewById(R.id.txtMiligramos);

        btnAgregar = (Button) getView().findViewById(R.id.btnAgregarM);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelarM);

        cboLab = (Spinner) getView().findViewById(R.id.cboLab);
        String[] tipos = {"Seleccione","Lab. Chile", "Walter White", "Lab. Knop", "BNI"};

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, tipos);
        cboLab.setAdapter(arrayAdapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombre.getText().toString().length()>0 &&txtMiligramos.getText().toString().length()>0 ){
                    if(cboLab.getSelectedItem().toString() != "Seleccione"){


                        double mili = Double.parseDouble(txtMiligramos.getText().toString());

                        if(mili >0){

                            conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA3", null, 1);

                            String username = txtNombre.getText().toString();
                            String[]usernameSSC = {username};
                            //Validacion2
                            SQLiteDatabase db = conexion.getReadableDatabase();
                            //Generamos la QUERY
                            //Almacenamos el resultado del select
                            Cursor cursor = db.rawQuery("SELECT * FROM medicamentos WHERE nombre = ?",usernameSSC);
                            boolean sswitch = true;

                            while(cursor.moveToNext()) {
                                if(cursor.getString(1).equals(username)){
                                    sswitch = false;
                                }
                            }


                            if(sswitch) {
                                String nombre = txtNombre.getText().toString();

                                String lab = cboLab.getSelectedItem().toString();

                                Cl_Medicamento medicamento1 = new Cl_Medicamento(nombre,lab,mili);




                                SQLiteDatabase database = conexion.getWritableDatabase();

                                if(database != null){
                                    ContentValues parametros = new ContentValues();
                                    parametros.put("nombre", medicamento1.getNombre());
                                    parametros.put("laboratorio", medicamento1.getLaboratorio());
                                    parametros.put("miligramos", medicamento1.getMiligramos());

                                    //Ejecutamos nuestro QUERY
                                    long i = database.insert("medicamentos", null, parametros);
                                    if(i > 0 ){
                                        txtNombre.setText("");
                                        txtMiligramos.setText("");
                                        cboLab.setSelection(0);
                                        Toast.makeText(getActivity(), "Registro insertado", Toast.LENGTH_LONG).show();

                                    }else{
                                        Toast.makeText(getActivity(), "Error al insertar registro", Toast.LENGTH_LONG).show();

                                    }

                                    //Cerramos la db
                                    database.close();
                                }
                            }else{
                                Toast.makeText(getActivity(), "Ya existe una mascota con ese nombre", Toast.LENGTH_SHORT).show();

                            }




                        }else{
                            Toast.makeText(getActivity(), "Los miligramos deben ser mayor a 0", Toast.LENGTH_SHORT).show();
                            txtMiligramos.setError("Mayor a 0");
                        }

                    }else{
                        Toast.makeText(getActivity(), "Seleccione un tipo válido", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                    txtNombre.setError("");
                    txtMiligramos.setError("");

                }




            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNombre.setText("");
                cboLab.setSelection(0);
                txtMiligramos.setText("");
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__agregar_m, container, false);
    }

}
