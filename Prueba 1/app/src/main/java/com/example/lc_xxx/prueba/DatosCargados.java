package com.example.lc_xxx.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DatosCargados extends AppCompatActivity {


    TextView txtNombre, txtApellido, txtTelefono, txtDireccion, txtUsername, txtTipo;
    Spinner cboPais;
    Button btnCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cargados);

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido = (TextView) findViewById(R.id.txtApellido);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtTipo = (TextView) findViewById(R.id.txtTipo);

        cboPais = (Spinner) findViewById(R.id.cboPais);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        //Volver de forma no fea
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //programo el cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i1);
            }
        });



        Bundle reciboDatos;
        //Recibo
        Intent i2 = getIntent();

        //Leo
        Bundle leer = i2.getExtras();

        String username = (String) leer.get("username");
        String pass = (String) leer.get("pass");

        String line;
        InputStream lector = this.getResources().openRawResource(R.raw.datos);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(lector));
        //contador
        int cont = 0;
        if (lector != null) {
            try {
                while ((line = buffer.readLine()) != null) {
                    if (line.split(";")[0].equals(username) && line.split(";")[1].equals(pass)) {
                        txtUsername.setText("Usuario: "+line.split(";")[1]);
                        txtTipo.setText("Tipo de usuario: "+line.split(";")[2]);
                        txtNombre.setText(line.split(";")[3]);
                        txtApellido.setText(line.split(";")[4]);
                        txtTelefono.setText(line.split(";")[5]);
                        txtDireccion.setText(line.split(";")[6]);
                        String[] pais = {line.split(";")[7]};
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pais);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cboPais.setAdapter(dataAdapter);


                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
