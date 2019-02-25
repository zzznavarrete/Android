package com.example.lc_xxx.prueba2.Fragments;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lc_xxx.prueba2.Modelo.Cl_Conexion;
import com.example.lc_xxx.prueba2.Modelo.Cl_Usuario;
import com.example.lc_xxx.prueba2.R;


public class F_Registro extends Fragment {

    Button btnCrear, btnCancelar, btnGoLogin;
    EditText txtUser, txtPass1, txtPass2;
    Cl_Conexion conexion;

    public F_Registro() {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        btnCrear = (Button) getView().findViewById(R.id.btnCrear);
        btnCancelar = (Button) getView().findViewById(R.id.btnCancelar);
        btnGoLogin = (Button) getView().findViewById(R.id.btnGoLogin);

        txtUser = (EditText) getView().findViewById(R.id.txtUser);
        txtPass1 = (EditText) getView().findViewById(R.id.txtPass1);
        txtPass2 = (EditText) getView().findViewById(R.id.txtPass2);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUser.setText("");
                txtPass1.setText("");
                txtPass2.setText("");
            }
        });


//Agregar usuario
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPass1.getText().length()>0 && txtPass2.getText().length()>0 &&  txtPass1.getText().toString().equals(txtPass2.getText().toString())){



                    conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);

                    String username = txtUser.getText().toString();
                    String[]usernameSSC = {username};
                    //Validacion2
                    SQLiteDatabase db = conexion.getReadableDatabase();
                    //Generamos la QUERY
                    //Almacenamos el resultado del select
                    Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE username = ?",usernameSSC);
                    boolean sswitch = false;
                    while(cursor.moveToNext()) {
                        if(cursor.getString(1).equals(username)){
                            sswitch = true;
                        }
                    }
                    db.close();
                    if(sswitch){
                        Toast.makeText(getActivity(), "Ya existe un usuario con ese nombre", Toast.LENGTH_SHORT).show();
                    }else{

                        SQLiteDatabase database = conexion.getWritableDatabase();

                        if(database!=null){

                            String user = txtUser.getText().toString();
                            String pass = txtPass1.getText().toString();

                            Cl_Usuario usuario = new Cl_Usuario(user, pass);

                            ContentValues parametros = new ContentValues();
                            parametros.put("username", usuario.getUsername());
                            parametros.put("pass", usuario.getPass());

                            //Ejecutamos el qery
                            long i = database.insert("usuarios", null, parametros);

                            if(i >0){
                                txtUser.setText("");
                                txtPass1.setText("");
                                txtPass2.setText("");
                                Toast.makeText(getActivity(), "Usuario ingresado", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), "Error al insertar user :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                        database.close();
                    }


                }else{
                    Toast.makeText(getActivity(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                }

            }
        });



        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragMana = getActivity().getSupportFragmentManager();
                fragMana.beginTransaction().replace(R.id.activity_login, new F_Login()).commit();
            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_f__registro, container, false);
    }

}
