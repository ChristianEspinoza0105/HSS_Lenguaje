package com.hss.generacion;

import com.hss.modelo.*;

public class GeneradorHTMLCSS {

    private static int contador = 0;

    public static String generarHTML(Documento documento) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("<title>").append(documento.getId()).append("</title>\n");
        html.append("<link rel=\"stylesheet\" href=\"estilos.css\">\n");
        html.append("</head>\n<body>\n");

        for (Elemento elemento : documento.getElementos()) {
            html.append(generarElementoHTML(elemento));
        }

        html.append("</body>\n</html>");
        return html.toString();
    }

    private static String generarElementoHTML(Elemento elemento) {
        StringBuilder html = new StringBuilder();
        String id = "elem" + (++contador);
        html.append("<").append(elemento.getTipo()).append(" id=\"").append(id).append("\">");
        html.append(elemento.getContenido());

        for (Elemento hijo : elemento.getHijos()) {
            html.append("\n").append(generarElementoHTML(hijo));
        }

        html.append("</").append(elemento.getTipo()).append(">\n");
        return html.toString();
    }

    public static String generarCSS(Documento documento) {
        StringBuilder css = new StringBuilder();
        contador = 0;
        for (Elemento elemento : documento.getElementos()) {
            css.append(generarEstiloCSS(elemento));
        }
        return css.toString();
    }

    private static String generarEstiloCSS(Elemento elemento) {
        StringBuilder css = new StringBuilder();
        String id = "elem" + (++contador);
        Estilo estilo = elemento.getEstilo();
        if (estilo != null && !estilo.getPropiedades().isEmpty()) {
            css.append("#").append(id).append(" {\n");
            for (Propiedad p : estilo.getPropiedades()) {
                css.append("  ").append(p.getNombre()).append(": ").append(p.getValor()).append(";\n");
            }
            css.append("}\n");
        }

        for (Elemento hijo : elemento.getHijos()) {
            css.append(generarEstiloCSS(hijo));
        }

        return css.toString();
    }
}