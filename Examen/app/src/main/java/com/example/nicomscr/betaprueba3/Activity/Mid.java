package com.example.nicomscr.betaprueba3.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nicomscr.betaprueba3.R;
import com.facebook.AccessToken;
import com.facebook.ProfileTracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class Mid extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    ImageView fotoGoogle;
    TextView txtNombreG, txtEmailG, txtIDG;
    GoogleApiClient googleApiClient;
    boolean logXGoogle = false;
    ImageView fotoFace;
    TextView txtNombreF, txtIDF;
    ProfileTracker profileTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid);



        String urlFoto = "https://i.pinimg.com/originals/fa/dc/34/fadc344b24d8c430fd2675047f044b7f.png";

        //TODO GOOGLE AQUI
        fotoGoogle = (ImageView) findViewById(R.id.fotoGogle);
        txtNombreG = (TextView) findViewById(R.id.txtNombreG);

        if(logXGoogle == false){
            String url = urlFoto;
            Glide.with(this).load(url).into(fotoGoogle);

        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();


        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        //TODO FACE AQUI




    }

    @Override
    protected void onStart() {
        super.onStart();

        //Recibo
        Intent i = getIntent();

        //Leo
        Bundle leer = i.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")){
                if(AccessToken.getCurrentAccessToken()==null){
                    goLoginScreen();
                }else{

                }
            }
            if(leer.get("loggin").equals("firebase")){



            }
            if(leer.get("loggin").equals("google")){
                logXGoogle = true;

                OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
                if(opr.isDone()){
                    GoogleSignInResult result = opr.get();
                    handleSignResult(result);
                }else{
                    opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                        @Override
                        public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                            handleSignResult(googleSignInResult);
                        }
                    });
                }

            }


        }
    }

    private void handleSignResult(GoogleSignInResult result) {

        if(result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();

            txtNombreG.setText(account.getDisplayName());
            Glide.with(this).load(account.getPhotoUrl()).into(fotoGoogle);

        }else{
            goLoginScreen();
        }
    }

    private void goLoginScreen() {
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    public void goMedico(View view){
        //Recibo
        Intent i = getIntent();

        //Leo
        Bundle leer = i.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")) {
                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                x.putExtra("loggin", "facebook");
                startActivity(x);
            }
            if(leer.get("loggin").equals("google")) {
                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                x.putExtra("loggin", "google");
                startActivity(x);
            }
            if(leer.get("loggin").equals("firebase")) {
                Intent x = new Intent(getApplicationContext(), MainActivity.class);
                x.putExtra("loggin", "firebase");
                startActivity(x);
            }


        }
    }

    public void goMedicamento(View view){
        //Recibo
        Intent i = getIntent();

        //Leo
        Bundle leer = i.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")) {
                Intent x = new Intent(getApplicationContext(), menu_medicamento.class);
                x.putExtra("loggin", "facebook");
                startActivity(x);
            }
            if(leer.get("loggin").equals("google")) {
                Intent x = new Intent(getApplicationContext(), menu_medicamento.class);
                x.putExtra("loggin", "google");
                startActivity(x);
            }
            if(leer.get("loggin").equals("firebase")) {
                Intent x = new Intent(getApplicationContext(), menu_medicamento.class);
                x.putExtra("loggin", "firebase");
                startActivity(x);
            }


        }
    }



    public void goHistorial(View view){
        //Recibo
        Intent i = getIntent();

        //Leo
        Bundle leer = i.getExtras();

        if(leer!=null){

            if(leer.get("loggin").equals("facebook")) {
                Intent x = new Intent(getApplicationContext(), Historial_medico.class);
                x.putExtra("loggin", "facebook");
                startActivity(x);
            }
            if(leer.get("loggin").equals("google")) {
                Intent x = new Intent(getApplicationContext(), Historial_medico.class);
                x.putExtra("loggin", "google");
                startActivity(x);
            }
            if(leer.get("loggin").equals("firebase")) {
                Intent x = new Intent(getApplicationContext(), Historial_medico.class);
                x.putExtra("loggin", "firebase");
                startActivity(x);
            }


        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}