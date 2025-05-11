package com.hss.gui.controladores;

import com.hss.analisis.Lexer;
import com.hss.analisis.Parser;
import com.hss.modelo.Documento;
import java.io.StringReader;

public class EditorController {

    public Documento analizarCodigo(String codigoFuente) throws Exception {
        StringReader reader = new StringReader(codigoFuente);
        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        return (Documento) parser.parse().value;
    }
}