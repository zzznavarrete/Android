package com.example.nicomscr.betaprueba3.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nicomscr.betaprueba3.Fragments.F_Login;
import com.example.nicomscr.betaprueba3.Fragments.F_Registro;
import com.example.nicomscr.betaprueba3.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    ImageView i_tittle_es, i_tittle_en, i_tittle_jp;

    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener fireAuthStateListener;


    LoginButton btnFace;
    CallbackManager callbackManager;

    EditText txtEmail, txtPass;
    Button btnLogin, btnGuardar, btnRegistrar;
    FirebaseAuth.AuthStateListener authStateListener;
    SignInButton btnGoogle;
    GoogleApiClient googleApiClient;
    public static final int SIGN_IN_CODE = 777;

     FirebaseAuth mAuth;
     FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        //TODO GOOGLE Abjo

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        btnGoogle = (SignInButton) findViewById(R.id.btnGoogle);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i, SIGN_IN_CODE);
            }
        });


        //TODO GOOGLE Arriba


        i_tittle_es = (ImageView) findViewById(R.id.foto_es);
        i_tittle_es.setVisibility(View.GONE);

        i_tittle_en = (ImageView) findViewById(R.id.foto_en);
        i_tittle_en.setVisibility(View.GONE);

        i_tittle_jp = (ImageView) findViewById(R.id.foto_jp);
        i_tittle_jp.setVisibility(View.GONE);

        btnRegistrar = (Button) findViewById(R.id.btnGoRegistro);
        txtEmail = (EditText)findViewById(R.id.txtUser);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);





        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtEmail.getText().toString().trim().equalsIgnoreCase("")){
                    txtEmail.setError("Este campo no puede estar vacío");
                }else{
                    if(txtPass.getText().toString().trim().equalsIgnoreCase("")){
                        txtPass.setError("Este campo no puede estar vacío");
                    }else{
                        iniciarSesion(txtEmail.getText().toString(), txtPass.getText().toString());

                    }

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




    private void goMainScreenFace() {
        Intent i = new Intent(getApplicationContext(), Mid.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("loggin", "facebook");
        startActivity(i);
    }


    private void iniciarSesion(String email, String pass){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("LOGIN", "Sesión iniciada FIREBASE");
                    Toast.makeText(Login.this, "Sesión iniciada!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Mid.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("loggin", "firebase");
                    startActivity(i);
                }else{
                    Log.i("LOGIN", "Error Sesión: "+  task.getException().toString());
                    Toast.makeText(Login.this, "Error al iniciar sesión :(", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();



        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            //Si la sesión en google está iniciada vamos a la pantalla principal
        if(opr.isDone()){
                goMainScreen();
        }else{



        }


        Random r = new Random();
        int numRan = r.nextInt(453 - 100) + 100;



        if(numRan <= 151 ){
            i_tittle_es.setVisibility(View.VISIBLE);

        }else if (numRan <= 302) {
            i_tittle_en.setVisibility(View.VISIBLE);

        }else{
            i_tittle_jp.setVisibility(View.VISIBLE);

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }

    }
    //TODO GOOGLE ABAJO

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();
        }else{
            Toast.makeText(this, "No se pudo logear :(", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent i = new Intent(getApplicationContext(), Mid.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("loggin", "google");
        startActivity(i);

    }

    public void goTest(View view){
        Intent i = new Intent(getApplicationContext(), TestA.class);
        startActivity(i);
    }
}