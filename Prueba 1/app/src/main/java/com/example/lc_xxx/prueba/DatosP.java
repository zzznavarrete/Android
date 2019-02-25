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

public class DatosP extends AppCompatActivity {
        //Declaro // TODO
        EditText txtNombre, txtApellido, txtDireccion, txtTelefono;
        Spinner spiPais;
        Button btnSiguiente;

        final String DATOS = "datos";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_datos_p);



            //Volver de forma no fea
            if(getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

            txtNombre = (EditText) findViewById(R.id.txtNombre);
            txtApellido = (EditText) findViewById(R.id.txtApellido);
            txtDireccion = (EditText) findViewById(R.id.txtDireccion);
            txtTelefono = (EditText) findViewById(R.id.txtTelefono);


            spiPais = (Spinner) findViewById(R.id.cboPais);





            btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre = txtNombre.getText().toString();
                    String apellido = txtApellido.getText().toString();
                    String direccion = txtDireccion.getText().toString();
                    String telefono = txtTelefono.getText().toString();
                    String pais = spiPais.getSelectedItem().toString();
                    String[]datos = {nombre,apellido,direccion,telefono, pais};

                    Intent pasoDatos = new Intent(getApplicationContext(), DatosP2.class);
                    pasoDatos.putExtra(DATOS, datos);
                    startActivity(pasoDatos);
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





