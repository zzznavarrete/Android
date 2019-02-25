package com.example.lc_xxx.prueba2.Modelo;

/**
 * Created by LC_XXX on 17-10-2017.
 */

public class Cl_Usuario {

    String username, pass;

    public Cl_Usuario() {
    }

    public Cl_Usuario(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
