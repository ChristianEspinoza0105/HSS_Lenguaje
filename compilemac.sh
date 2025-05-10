#!/bin/bash

# ------------------------------------------------------------
# CONFIGURACIÓN
# ------------------------------------------------------------
JFLEX_PATH="/Users/christianespinoza/Downloads/jflex-1.9.1/lib/jflex-full-1.9.1.jar"
CUP_PATH="/Users/christianespinoza/Downloads/cup/java-cup-11b.jar"
PROJECT_PATH="$(cd "$(dirname "$0")"; pwd)"
SRC_PATH="$PROJECT_PATH/src/main/java"
BIN_PATH="$PROJECT_PATH/bin"
ANALISIS_PATH="$SRC_PATH/com/hss/analisis"
MAIN_CLASS="com.hss.MainApp"

# ------------------------------------------------------------
# FUNCIONES
# ------------------------------------------------------------

menu() {
    clear
    echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    echo " HSS_LANGUAGE - MENU PRINCIPAL"
    echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
    echo "1. Compilar todo el proyecto"
    echo "2. Generar Lexer con JFlex"
    echo "3. Generar Parser con CUP"
    echo "4. Compilar SOLO CUP (Parser.java y sym.java)"
    echo "5. Ejecutar programa"
    echo "6. Compilar + Ejecutar"
    echo "7. Salir"
    echo

    read -p "Seleccione una opción [1-7]: " option
    case $option in
        1) compilar ;;
        2) jflex ;;
        3) cup ;;
        4) compilar_cup ;;
        5) run ;;
        6) compilar && run ;;
        7) exit 0 ;;
        *) echo "Opción inválida"; sleep 1; menu ;;
    esac
}

jflex() {
    clear
    echo "Generando analizador léxico con JFlex..."
    java -jar "$JFLEX_PATH" -d "$ANALISIS_PATH" "$ANALISIS_PATH/Lexer.flex"
    if [ $? -ne 0 ]; then
        echo "❌ Error al generar el analizador léxico"
    else
        echo "✅ Lexer generado correctamente"
    fi
    read -p "Presiona Enter para continuar..."
}

cup() {
    clear
    echo "Generando analizador sintáctico con CUP..."
    cd "$ANALISIS_PATH"
    java -jar "$CUP_PATH" -parser Parser -symbols sym -destdir "$ANALISIS_PATH" "$ANALISIS_PATH/Parser.cup"
    if [ $? -ne 0 ]; then
        echo "❌ Error al generar el analizador sintáctico"
    else
        echo "✅ Parser generado correctamente"
    fi
    cd "$PROJECT_PATH"
    read -p "Presiona Enter para continuar..."
}

compilar() {
    clear
    echo "Compilando todo el proyecto..."

    mkdir -p "$BIN_PATH"
    find "$SRC_PATH" -name "*.java" > sources.txt

    javac -cp "$CUP_PATH:$JFLEX_PATH" -d "$BIN_PATH" @sources.txt
    if [ $? -ne 0 ]; then
        echo "❌ Error durante la compilación"
    else
        echo "✅ Compilación completada exitosamente"
    fi

    rm sources.txt
    read -p "Presiona Enter para continuar..."
}

compilar_cup() {
    clear
    echo "Compilando SOLO archivos generados por CUP..."
    mkdir -p "$BIN_PATH"

    javac -cp "$CUP_PATH" -d "$BIN_PATH" "$ANALISIS_PATH/Parser.java" "$ANALISIS_PATH/sym.java"
    if [ $? -ne 0 ]; then
        echo "❌ Error al compilar archivos CUP"
    else
        echo "✅ Archivos CUP compilados correctamente"
    fi
    read -p "Presiona Enter para continuar..."
}

run() {
    clear
    echo "Ejecutando clase principal: $MAIN_CLASS"
    java -cp "$BIN_PATH:$CUP_PATH:$JFLEX_PATH" "$MAIN_CLASS"
    if [ $? -ne 0 ]; then
        echo "❌ Error al ejecutar el programa"
    fi
    read -p "Presiona Enter para continuar..."
}

# ------------------------------------------------------------
# EJECUTAR MENÚ
# ------------------------------------------------------------
menu