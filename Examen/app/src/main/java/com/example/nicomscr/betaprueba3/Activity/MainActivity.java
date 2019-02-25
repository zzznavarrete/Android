package com.example.nicomscr.betaprueba3.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nicomscr.betaprueba3.Fragments.Paciente.F_AgregarP;
import com.example.nicomscr.betaprueba3.Fragments.Paciente.F_EliminarP;
import com.example.nicomscr.betaprueba3.Fragments.Paciente.F_Historial;
import com.example.nicomscr.betaprueba3.Fragments.Paciente.F_ListarP;
import com.example.nicomscr.betaprueba3.Fragments.Paciente.F_ModificarP;
import com.example.nicomscr.betaprueba3.Fragments.Paciente.F_PresentacionP;
import com.example.nicomscr.betaprueba3.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FragmentManager f = getSupportFragmentManager();
        f.beginTransaction().replace(R.id.content_main, new F_PresentacionP()).commit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //Recibo
                    Intent x = getIntent();

                    //Leo
                    Bundle leer = x.getExtras();

                    if(leer!=null){

                        if(leer.get("loggin").equals("facebook")){
                            Intent i = new Intent(getApplicationContext(),  Menus2.class);
                            i.putExtra("loggin", "facebook");
                            startActivity(i);
                        }
                        if(leer.get("loggin").equals("firebase")){
                            Intent i = new Intent(getApplicationContext(),  Menus2.class);
                            i.putExtra("loggin", "firebase");
                            startActivity(i);

                        }
                        if(leer.get("loggin").equals("google")){
                            Intent i = new Intent(getApplicationContext(),  Menus2.class);
                            i.putExtra("loggin", "google");
                            startActivity(i);

                        }
                    }



                }

        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salir) {

            //Recibo
            Intent x = getIntent();

            //Leo
            Bundle leer = x.getExtras();

            if(leer!=null){

                if(leer.get("loggin").equals("facebook")){
                    LoginManager.getInstance().logOut();
                    goLoginScreen();
                }
                if(leer.get("loggin").equals("firebase")){
                    goLoginScreen();

                }
                if(leer.get("loggin").equals("google")){
                    revoke();
                }
            }




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager f = getSupportFragmentManager();


        if (id == R.id.nav_agregarP) {
            f.beginTransaction().replace(R.id.content_main, new F_AgregarP()).commit();
            return true;

        } else if (id == R.id.nav_modificarP) {
            f.beginTransaction().replace(R.id.content_main, new F_ModificarP()).commit();
            return true;

        } else if (id == R.id.nav_listarP) {
            f.beginTransaction().replace(R.id.content_main, new F_ListarP()).commit();
            return true;

        } else if (id == R.id.nav_eliminarP) {
            f.beginTransaction().replace(R.id.content_main, new F_EliminarP()).commit();
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goLoginScreen() {
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private void revoke(){
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull  Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(MainActivity.this, "No se pudo revokar sesión :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
