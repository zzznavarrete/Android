package com.example.lc_xxx.prueba2.Modelo;

/**
 * Created by LC_XXX on 17-10-2017.
 */

public class Cl_Mascota {

    private String nombre, tipo, raza;
    private int edad;

    public Cl_Mascota() {
    }

    //Constructor para eliminar sólo por éste parámetro
    public Cl_Mascota(String nombre) {
        this.nombre = nombre;
    }

    public Cl_Mascota(String nombre, String tipo, String raza, int edad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
