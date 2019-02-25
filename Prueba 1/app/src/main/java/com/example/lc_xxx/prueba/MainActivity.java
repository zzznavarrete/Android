package com.example.lc_xxx.prueba;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Declaro variables
    EditText txtUser, txtPass;
    Button btnEntrar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Les doy vida
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPass = (EditText) findViewById(R.id.txtPass);


        btnCancelar = (Button) findViewById(R.id.btnCancelar);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtUser.setText("");
                txtPass.setText("");
            }
        });



    }


    public void Login(View view) throws IOException {
        //Creamos linea
        String line;
        //Creamos 'leedor'
        InputStream lector = this.getResources().openRawResource(R.raw.datos);
        //Lo almacenamos en buffer
        BufferedReader buffer = new BufferedReader(new InputStreamReader(lector));
        //Creamos contador
        int cont = 0;
        //Validamos que haya leído algún archivo
        if(lector != null){
            //Hacemos lo siguiente mientras la linea no esté vacia o nula
            while((line = buffer.readLine()) != null){
                //Ahora preguntamos por lo ingresado por el usuario
                //Esto para preguntar  por columnas
                if(line.split(";")[0].equals(txtUser.getText().toString()) && line.split(";")[1].equals(txtPass.getText().toString()) ){
                    cont = 1;



                    //Creamos el mensaje
                    Toast.makeText(this, "Bienvenido "+ txtUser.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent entrarDatos = new Intent(getApplicationContext(), MenuActivity.class);
                    //Enviar datos
                    entrarDatos.putExtra("username", txtUser.getText().toString());
                    entrarDatos.putExtra("pass", txtPass.getText().toString());
                    startActivity(entrarDatos);
                }

            }
            if(cont == 0){
                Toast.makeText(this, "No se encuentran coincidencias", Toast.LENGTH_SHORT).show();
            }
            //Cerramos el archivo
            lector.close();

        }

    }
}
