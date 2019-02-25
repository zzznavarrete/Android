package com.example.nicomscr.betaprueba3.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nicomscr.betaprueba3.R;

public class Menus2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus2);


    }

    public void goMedicamentoMenu(View view){
        //Recibo
        Intent x = getIntent();

        //Leo
        Bundle leer = x.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")){
                Intent i = new Intent(getApplicationContext(),  menu_medicamento.class);
                i.putExtra("loggin", "facebook");
                startActivity(i);
            }
            if(leer.get("loggin").equals("firebase")){
                Intent i = new Intent(getApplicationContext(),  menu_medicamento.class);
                i.putExtra("loggin", "firebase");
                startActivity(i);

            }
            if(leer.get("loggin").equals("google")){
                Intent i = new Intent(getApplicationContext(),  menu_medicamento.class);
                i.putExtra("loggin", "google");
                startActivity(i);

            }
        }


    }
    public void goMedicoMenu(View view){
        //Recibo
        Intent x = getIntent();

        //Leo
        Bundle leer = x.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")){
                Intent i = new Intent(getApplicationContext(),  MainActivity.class);
                i.putExtra("loggin", "facebook");
                startActivity(i);
            }
            if(leer.get("loggin").equals("firebase")){
                Intent i = new Intent(getApplicationContext(),  MainActivity.class);
                i.putExtra("loggin", "firebase");
                startActivity(i);

            }
            if(leer.get("loggin").equals("google")){
                Intent i = new Intent(getApplicationContext(),  MainActivity.class);
                i.putExtra("loggin", "google");
                startActivity(i);

            }
        }


    }

    public void goHistorial(View view){
        //Recibo
        Intent x = getIntent();

        //Leo
        Bundle leer = x.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")){
                Intent i = new Intent(getApplicationContext(),  Historial_medico.class);
                i.putExtra("loggin", "facebook");
                startActivity(i);
            }
            if(leer.get("loggin").equals("firebase")){
                Intent i = new Intent(getApplicationContext(),  Historial_medico.class);
                i.putExtra("loggin", "firebase");
                startActivity(i);

            }
            if(leer.get("loggin").equals("google")){
                Intent i = new Intent(getApplicationContext(),  Historial_medico.class);
                i.putExtra("loggin", "google");
                startActivity(i);

            }
        }


    }
}
