package com.hss.modelo;

import java.util.ArrayList;
import java.util.List;

public class Elemento {
    private String tipo;
    private String contenido;
    private List<Elemento> hijos;
    private Estilo estilo;

    public Elemento(String tipo, String contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
        this.hijos = new ArrayList<>();
        this.estilo = new Estilo();
    }

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    private String nombre;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public void agregarHijo(Elemento hijo) {
        this.hijos.add(hijo);
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public List<Elemento> getHijos() {
        return hijos;
    }

    public Estilo getEstilo() {
        return estilo;
    }
}