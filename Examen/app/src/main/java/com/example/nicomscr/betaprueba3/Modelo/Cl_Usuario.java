package com.example.nicomscr.betaprueba3.Modelo;


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
