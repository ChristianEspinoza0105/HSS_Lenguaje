package com.hss.modelo;

import java.util.ArrayList;
import java.util.List;

public class Estilo {
    private List<Propiedad> propiedades;

    public Estilo() {
        this.propiedades = new ArrayList<>();
    }

    public void agregarPropiedad(Propiedad p) {
        this.propiedades.add(p);
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }
}