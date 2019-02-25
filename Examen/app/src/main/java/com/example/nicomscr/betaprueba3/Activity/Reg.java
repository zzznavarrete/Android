package com.example.nicomscr.betaprueba3.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nicomscr.betaprueba3.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class Reg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        if(AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }

    }

    private void goLoginScreen() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void salirFace(View view){
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
