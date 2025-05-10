package com.hss.analisis;

import java_cup.runtime.*;

%%

%class Lexer
%unicode
%cup
%line
%column

%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

Identifier = [a-zA-Z_][a-zA-Z0-9_-]*
Number = [0-9]+(\.[0-9]+)?
HexColor = "#"[0-9a-fA-F]{6}
StringLiteral = \"([^\"\\]|\\.)*\" | \'([^\'\\]|\\.)*\'

Comment = "//".*

%%

"page"          { return symbol(sym.PAGE); }
"section"       { return symbol(sym.SECTION); }
"text"          { return symbol(sym.TEXT); }
"button"        { return symbol(sym.BUTTON); }
"image"         { return symbol(sym.IMAGE); }

"width"         { return symbol(sym.WIDTH); }
"height"        { return symbol(sym.HEIGHT); }
"color"         { return symbol(sym.COLOR); }
"background"    { return symbol(sym.BACKGROUND); }
"font-size"     { return symbol(sym.FONT_SIZE); }
"margin"        { return symbol(sym.MARGIN); }
"padding"       { return symbol(sym.PADDING); }

"with"          { return symbol(sym.WITH); }

"doc"           { return symbol(sym.DOC); }
"enddoc"        { return symbol(sym.ENDDOC); }

"{"             { return symbol(sym.LBRACE); }
"}"             { return symbol(sym.RBRACE); }
"="             { return symbol(sym.EQUALS); }

{Number}        { return symbol(sym.NUMBER, Double.parseDouble(yytext())); }
{HexColor}      { return symbol(sym.HEX_COLOR, yytext().substring(1)); }
{StringLiteral} { return symbol(sym.STRING, yytext().substring(1, yytext().length()-1)); }
{Identifier}    { return symbol(sym.ID, yytext()); }

{Comment}       { /* ignorar comentarios */ }
{WhiteSpace}    { /* ignorar espacios */ }

.               { throw new Error("Illegal character: " + yytext()); }