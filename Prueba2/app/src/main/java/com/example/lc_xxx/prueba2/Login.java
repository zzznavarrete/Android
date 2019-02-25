package com.example.lc_xxx.prueba2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lc_xxx.prueba2.Fragments.F_Login;
import com.example.lc_xxx.prueba2.Fragments.F_Registro;

public class Login extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        FragmentManager f = getSupportFragmentManager();
        f.beginTransaction().replace(R.id.activity_login, new F_Login()).commit();



    }

}
