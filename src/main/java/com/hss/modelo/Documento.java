package com.hss.modelo;

import java.util.ArrayList;
import java.util.List;

public class Documento {
    private String id;
    private List<Elemento> elementos;

    public Documento() {
        this.elementos = new ArrayList<>();
    }

    public Documento(String id) {
        this.id = id;
        this.elementos = new ArrayList<>();
    }

    public void agregarElemento(Elemento e) {
        this.elementos.add(e);
    }

    public String getId() {
        return id;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }
}