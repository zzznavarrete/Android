package com.example.nicomscr.betaprueba3.Modelo;

/**
 * Created by nicomscr on 11/23/2017.
 */

public class Cl_Medicamento {

    String nombre, laboratorio;
    Double miligramos;


    public Cl_Medicamento() {
    }

    //Constructor para eliminar sólo por éste parámetro
    public Cl_Medicamento(String nombre) {
        this.nombre = nombre;
    }

    public Cl_Medicamento(String nombre, String laboratorio, Double miligramos) {
        this.nombre = nombre;
        this.laboratorio = laboratorio;
        this.miligramos = miligramos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Double getMiligramos() {
        return miligramos;
    }

    public void setMiligramos(Double miligramos) {
        this.miligramos = miligramos;
    }
}
