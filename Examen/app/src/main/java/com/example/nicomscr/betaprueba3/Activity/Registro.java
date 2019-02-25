package com.example.nicomscr.betaprueba3.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nicomscr.betaprueba3.Conexion.Cl_Conexion;
import com.example.nicomscr.betaprueba3.Fragments.F_Login;
import com.example.nicomscr.betaprueba3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    EditText txtEmail, txtPass, txtPass2;
    Button btnLogin, btnCancelar, btnCrear;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        txtEmail = (EditText)findViewById(R.id.txtUserR);
        txtPass = (EditText) findViewById(R.id.txtPass1R );
        txtPass2 = (EditText) findViewById(R.id.txtPass2R);
        btnLogin = (Button) findViewById(R.id.btnGoLoginR);
        btnCrear = (Button)findViewById(R.id.btnCrearR);
        btnCancelar = (Button) findViewById(R.id.btnCancelarR);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEmail.setText("");
                txtPass.setText("");
                txtPass2.setText("");
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPass.getText().toString().equals(txtPass2.getText().toString())){
                    if(txtPass.getText().length()>=6){
                        registrarUsuario(txtEmail.getText().toString(), txtPass.getText().toString());
                    }else{
                        txtPass.setError("La clave debe ser de al menos 6 dígitos");
                    }
                }else{
                    txtPass.setError("Las contraseñas deben ser iguales");
                    Toast.makeText(Registro.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
            }
            }
        });

        //
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d("LOGIN", "SESION INICIADA: "+user.getUid());

                }else{
                    Log.d("LOGIN", "Sesion cerrada!");
                }
            }
        };

    }


    private void registrarUsuario(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("LOGIN", "Usuario Creado!");
                    txtEmail.setText("");
                    txtPass.setText("");
                    txtPass2.setText("");
                    Toast.makeText(Registro.this, "Éxito! Usuario creado", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i("LOGIN", "Usuario NO CREADO! : "+task.getException().toString());
                    Toast.makeText(getApplicationContext(), "Error al crear usuario :(", Toast.LENGTH_SHORT).show();


                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }

    }
}