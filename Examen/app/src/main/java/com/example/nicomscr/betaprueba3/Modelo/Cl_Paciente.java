package com.example.nicomscr.betaprueba3.Modelo;

/**
 * Created by nicomscr on 11/23/2017.
 */

public class Cl_Paciente {

    String rut, nombre, apellido, direccion, medicamento;
    int edad;

    public Cl_Paciente() {
    }

    //Para eliminar
    public Cl_Paciente(String rut) {
        this.rut = rut;
    }

    public Cl_Paciente(String rut, String nombre, String apellido, String direccion, String medicamento, int edad) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.medicamento = medicamento;
        this.edad = edad;
    }




    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
