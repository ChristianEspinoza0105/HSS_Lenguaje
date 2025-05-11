package com.hss.generacion;

import com.hss.modelo.*;

public class GeneradorHTMLCSS {

    private static int contador = 0;

    public static String generarHTML(Documento documento) {
        contador = 0;
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n<html>\n<head>\n");
        html.append("  <meta charset=\"UTF-8\">\n");
        html.append("  <title>").append(documento.getId()).append("</title>\n");
        html.append("  <link rel=\"stylesheet\" href=\"estilos.css\">\n");
        html.append("</head>\n<body>\n");

        for (Elemento elemento : documento.getElementos()) {
            html.append(generarElementoHTML(elemento, 1));
        }

        html.append("</body>\n</html>");
        return html.toString();
    }

    private static String generarElementoHTML(Elemento elemento, int nivel) {
        StringBuilder html = new StringBuilder();
        String indent = "  ".repeat(nivel);
        String id = "elem" + (++contador);
        elemento.setId(id);

        switch (elemento.getTipo()) {
            case "section":
                html.append(indent).append("<section id=\"").append(id).append("\">");
                break;
            case "text":
                html.append(indent).append("<p id=\"").append(id).append("\">");
                break;
            case "image":
                html.append(indent).append("<img id=\"").append(id).append("\" src=\"")
                    .append(elemento.getContenido()).append("\" alt=\"").append(id).append("\">");
                return html.toString();
            case "button":
                html.append(indent).append("<button id=\"").append(id).append("\">")
                    .append(elemento.getContenido()).append("</button>");
                return html.toString();
            default:
                html.append(indent).append("<div id=\"").append(id).append("\">");
        }

        if (elemento.getContenido() != null && !elemento.getContenido().isEmpty()) {
            String contenidoConSaltos = elemento.getContenido().replace("\n", "<br>\n" + indent + "  ");
            html.append("\n").append(indent).append("  ").append(contenidoConSaltos).append("\n");
        }

        if (!elemento.getHijos().isEmpty()) {
            html.append("\n");
            for (Elemento hijo : elemento.getHijos()) {
                html.append(generarElementoHTML(hijo, nivel + 1));
            }
            html.append(indent);
        }

        html.append("</").append(elemento.getTipo()).append(">\n");
        return html.toString();
    }

    public static String generarCSS(Documento documento) {
        contador = 0;
        StringBuilder css = new StringBuilder();
        for (Elemento elemento : documento.getElementos()) {
            css.append(generarEstiloCSS(elemento));
        }
        return css.toString();
    }

    private static String generarEstiloCSS(Elemento elemento) {
        StringBuilder css = new StringBuilder();
        String id = "elem" + (++contador);

        if (elemento.getId() != null) {
            id = elemento.getId();
        }

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