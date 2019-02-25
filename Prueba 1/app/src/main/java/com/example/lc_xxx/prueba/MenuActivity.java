package com.example.lc_xxx.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnRegistro, btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnRegistro = (Button)findViewById(R.id.btnRegistro);
        btnPerfil= (Button)findViewById(R.id.btnPerfil);


        //Volver de forma no fea
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }





        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i2 = new Intent(getApplicationContext(), actDatos.class);
                startActivity(i2);
            }
        });






      btnPerfil.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {

              //Recibo
              Intent entrarDatos = getIntent();

              //Leo
              Bundle leer = entrarDatos.getExtras();

              //Validamos que no esté vacio
              if(leer!=null){
                  String nombreuser = (String) leer.get("username");
                  String pass = (String) leer.get("pass");

                  Intent i2 = new Intent(getApplicationContext(), DatosCargados.class );
                  i2.putExtra("username", nombreuser);
                  i2.putExtra("pass",pass);
                  startActivity(i2);

              }

              return false;
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





