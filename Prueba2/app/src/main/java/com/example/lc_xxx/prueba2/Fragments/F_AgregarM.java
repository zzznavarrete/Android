package com.example.lc_xxx.prueba2.Fragments;


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

import com.example.lc_xxx.prueba2.Modelo.Cl_Conexion;
import com.example.lc_xxx.prueba2.Modelo.Cl_Mascota;
import com.example.lc_xxx.prueba2.R;


public class F_AgregarM extends Fragment {

    EditText txtNombre, txtRaza, txtEdad;
    Button btnAgregar, btnCancelar;
    Spinner txtTipo;
    Cl_Conexion conexion;

    public F_AgregarM() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        txtNombre = (EditText) getView().findViewById(R.id.txtNombre);
        txtRaza = (EditText) getView().findViewById(R.id.txtRaza);
        txtEdad = (EditText) getView().findViewById(R.id.txtEdad);

        btnAgregar = (Button) getView().findViewById(R.id.btnAgregar);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelar);

        txtTipo = (Spinner) getView().findViewById(R.id.txtTipo);

        String[] tipos = {"Seleccione","Perro", "Gato", "Ave", "Otro"};

        ArrayAdapter<CharSequence> arrayAdapter =
                new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, tipos);
        txtTipo.setAdapter(arrayAdapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNombre.getText().toString().length()>0 &&txtRaza.getText().toString().length()>0 &&txtEdad.getText().toString().length()>0){
                    if(txtTipo.getSelectedItem().toString() != "Seleccione"){


                    int edad = Integer.parseInt(txtEdad.getText().toString());

                    if(edad >0){

                        conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);

                        String username = txtNombre.getText().toString();
                        String[]usernameSSC = {username};
                        //Validacion2
                        SQLiteDatabase db = conexion.getReadableDatabase();
                        //Generamos la QUERY
                        //Almacenamos el resultado del select
                        Cursor cursor = db.rawQuery("SELECT * FROM mascotas WHERE nombre = ?",usernameSSC);
                        boolean sswitch = true;

                        while(cursor.moveToNext()) {
                            if(cursor.getString(1).equals(username)){
                                sswitch = false;
                            }
                        }


                        if(sswitch) {
                            String nombre = txtNombre.getText().toString();
                            String raza = txtRaza.getText().toString();

                            String tipo = txtTipo.getSelectedItem().toString();

                            Cl_Mascota mascota1 = new Cl_Mascota(nombre, tipo,raza,edad);




                            SQLiteDatabase database = conexion.getWritableDatabase();

                            if(database != null){
                                ContentValues parametros = new ContentValues();
                                parametros.put("nombre", mascota1.getNombre());
                                parametros.put("tipo", mascota1.getTipo());
                                parametros.put("raza", mascota1.getRaza());
                                parametros.put("edad", mascota1.getEdad());

                                //Ejecutamos nuestro QUERY
                                long i = database.insert("mascotas", null, parametros);
                                if(i > 0 ){
                                    txtNombre.setText("");
                                    txtEdad.setText("");
                                    txtRaza.setText("");
                                    txtTipo.setSelection(0);
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
                        Toast.makeText(getActivity(), "La edad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    }

                    }else{
                        Toast.makeText(getActivity(), "Seleccione un tipo válido", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }




            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNombre.setText("");
                txtTipo.setSelection(0);
                txtRaza.setText("");
                txtEdad.setText("");
            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__agregar_m, container, false);
    }

}
