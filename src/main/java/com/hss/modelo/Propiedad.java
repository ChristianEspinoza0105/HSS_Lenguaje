package com.hss.modelo;

public class Propiedad {
    private String nombre;
    private String valor;

    public Propiedad(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }
}