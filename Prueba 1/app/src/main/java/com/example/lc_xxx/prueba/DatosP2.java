package com.example.lc_xxx.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import static com.example.lc_xxx.prueba.actDatos.DATOS;
public class DatosP2 extends AppCompatActivity {

    TextView txtNombre, txtApellido, txtTelefono, txtDireccion;
    Spinner cboPais;
    Button btnCancelar2, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_p2);


        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido =(TextView) findViewById(R.id.txtApellido);
        txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        txtDireccion = (TextView) findViewById(R.id.txtDireccion);

        cboPais = (Spinner) findViewById(R.id.cboPais);

        btnCancelar2 = (Button) findViewById(R.id.btnCancelar2);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);


        btnCancelar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), actDatos.class);
                startActivity(i1);
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i2);
            }
        });


        Bundle reciboDatos;
        //Recibo
        Intent pasoDatillos = getIntent();

        //Leo
        Bundle leer = pasoDatillos.getExtras();

        //Validamos que no esté vacio
        if(leer!=null){

            String datos[] = (String[]) leer.get(DATOS);
            txtNombre.setText(datos[0]);
            txtApellido.setText(datos[1]);
            txtTelefono.setText(datos[2]);
            txtDireccion.setText(datos[3]);

            //Asignamos el valor de la variable
            String[] pais = {datos[4]};

            //Cargamos pais al spinner
            cboPais.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pais));




        }






    }




}
