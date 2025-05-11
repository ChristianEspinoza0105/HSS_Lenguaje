package com.hss.modelo;

import java.util.ArrayList;
import java.util.List;

public class Estilo {
    private List<Propiedad> propiedades;

    public Estilo() {
        this.propiedades = new ArrayList<>();
    }

    public void agregarPropiedad(Propiedad propiedad) {
        this.propiedades.add(propiedad);
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }
}