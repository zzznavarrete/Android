package com.example.lc_xxx.prueba2.Fragments;


import android.content.Intent;
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

import com.example.lc_xxx.prueba2.MainActivity;
import com.example.lc_xxx.prueba2.Modelo.Cl_Conexion;
import com.example.lc_xxx.prueba2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class F_Login extends Fragment {

    EditText txtUser, txtPass;
    Button btnGoRegistro, btnEntrar;
    Cl_Conexion conexion;

    public F_Login() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        txtUser = (EditText) getView().findViewById(R.id.txtUser);
        txtPass = (EditText) getView().findViewById(R.id.txtPass) ;
        btnGoRegistro = (Button) getView().findViewById(R.id.btnGoRegistro);
        btnEntrar = (Button) getView().findViewById(R.id.btnIngresar);

        btnGoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragMana = getActivity().getSupportFragmentManager();
                fragMana.beginTransaction().replace(R.id.activity_login, new F_Registro()).commit();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Conectar con la BD
                conexion = new Cl_Conexion(getActivity().getApplicationContext(), "BD_PRUEBA2", null, 1);
                SQLiteDatabase db = conexion.getReadableDatabase();

                String user = txtUser.getText().toString();
                String pass = txtPass.getText().toString();

                try{
                    Cursor cursor = db.rawQuery("select username, pass from  usuarios  where username  = ? AND pass = ? ", new String[] { user, pass});
                    cursor.moveToFirst();
                    Toast.makeText(getActivity(),"Bienvenido "+ cursor.getString(0), Toast.LENGTH_SHORT).show();
                    Intent sesion = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    boolean variableS = true;
                    sesion.putExtra("sesion", variableS);
                    startActivity(sesion);

                }catch (Exception e){
                    Toast.makeText(getActivity(),"Credenciales incorrectas", Toast.LENGTH_SHORT).show();

                }
                db.close();

            }
        });



        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f__login, container, false);
    }




}
