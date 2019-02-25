package com.example.lc_xxx.prueba;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class actDatos extends AppCompatActivity {

    //Declaro
    EditText txtNombre, txtApellido, txtTelefono, txtDireccion;
    Spinner cboPais;
    Button btnSiguiente, btnCancelar;


    public static final String DATOS = "datos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_datos);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtDireccion = (EditText) findViewById(R.id.txtDireccion);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);


        cboPais = (Spinner) findViewById(R.id.cboPais);

        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);



        //Volver de forma no fea
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




        List<String> values = new ArrayList<String>();
        values.add("Chile");
        values.add("Mordor");
        values.add("Brasilia");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cboPais.setAdapter(dataAdapter);





        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtPais = cboPais.getSelectedItem().toString();
                String datos[] = {txtNombre.getText().toString(), txtApellido.getText().toString(), txtTelefono.getText().toString(), txtDireccion.getText().toString(), txtPais};

                Intent pasoDatillos = new Intent(getApplicationContext(), DatosP2.class);
                pasoDatillos.putExtra(DATOS, datos);
                startActivity(pasoDatillos);

            }
        });



    }


    //Va fuera del OnCreate
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }





    }
