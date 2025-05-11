package com.hss.gui.controladores;

import com.hss.generacion.GeneradorHTMLCSS;
import com.hss.modelo.Documento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.web.WebView;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import javafx.scene.layout.BorderPane;
import org.fxmisc.richtext.LineNumberFactory;
import javafx.scene.control.Button;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController implements Initializable {

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private HBox inputConsole;

    @FXML
    private BorderPane root;
    private CodeArea codeArea;

    @FXML
    private ImageView previewIcon;

    @FXML
    private ImageView menuIcon;

    @FXML
    private ImageView inputIcon;

    @FXML
    private Button analyzeButton;

    @FXML
    private VBox previewPane;

    @FXML
    private javafx.scene.control.TextArea inputTextArea;

    @FXML
    private Button saveButton;

    @FXML
    private void toggleInputConsole() {
        boolean isVisible = inputConsole.isVisible();
        inputConsole.setVisible(!isVisible);
        inputConsole.setManaged(!isVisible);
    }

    @FXML
    private void closeInputConsole() {
        inputConsole.setVisible(false);
        inputConsole.setManaged(false);
    }

    @FXML
    private void handleAnalyze(ActionEvent event) {
        inputConsole.setVisible(true);
        inputConsole.setManaged(true);

        String code = codeArea.getText();
        System.out.println("Analizando código...");

        EditorController editor = new EditorController();

        try {
            Documento documento = editor.analizarCodigo(code);
            inputTextArea.setText("✔ Análisis exitoso. No se encontraron errores.");
        } catch (Exception e) {
            StringBuilder errorBuilder = new StringBuilder();

            errorBuilder.append("❌ Error encontrado durante el análisis:\n");
            errorBuilder.append("Mensaje: ").append(e.getMessage()).append("\n");

            inputTextArea.setText(errorBuilder.toString());

            System.err.println(errorBuilder.toString());
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePreview() {
        String code = codeArea.getText();

        try {
            EditorController editor = new EditorController();
            Documento documento = editor.analizarCodigo(code);

            boolean isVisible = previewPane.isVisible();
            previewPane.setVisible(!isVisible);
            previewPane.setManaged(!isVisible);

            if (!isVisible) {
                String htmlContent = GeneradorHTMLCSS.generarHTML(documento);
                String cssContent = GeneradorHTMLCSS.generarCSS(documento);
                String finalHtml = "<style>" + cssContent + "</style>" + htmlContent;

                previewPane.getChildren().clear();

                WebView webView = new WebView();
                webView.setPrefWidth(800);
                webView.setPrefHeight(1000);
                webView.getEngine().loadContent(finalHtml, "text/html");

                previewPane.getChildren().add(webView);
            }

        } catch (Exception e) {
            StringBuilder errorBuilder = new StringBuilder();
            errorBuilder.append("❌ Error encontrado durante el análisis:\n");
            errorBuilder.append("Mensaje: ").append(e.getMessage()).append("\n");
            inputTextArea.setText(errorBuilder.toString());

            System.err.println(errorBuilder.toString());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            try {
                String code = codeArea.getText();
                EditorController editor = new EditorController();
                Documento documento = editor.analizarCodigo(code);

                String htmlContent = GeneradorHTMLCSS.generarHTML(documento);
                String cssContent = GeneradorHTMLCSS.generarCSS(documento);
                String finalHtml = "<style>" + cssContent + "</style>" + htmlContent;

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(finalHtml);
                    inputTextArea.setText("✔ Archivo guardado exitosamente.");
                } catch (IOException e) {
                    inputTextArea.setText("❌ Error al guardar el archivo.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                inputTextArea.setText("❌ Error al generar el archivo.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleMaximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage != null) {
            stage.setFullScreen(!stage.isFullScreen());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setEditable(true);
        codeArea.setWrapText(true);
        codeArea.setStyle("-fx-background-color: #282c34; -fx-font-size: 20px; -fx-font-family: 'JetBrains Mono'; -fx-fill: white;");

        VirtualizedScrollPane<CodeArea> scrollPane = new VirtualizedScrollPane<>(codeArea);
        root.setCenter(scrollPane);

        root.getStylesheets().add(getClass().getResource("/css/estilo.css").toExternalForm());

        applySyntaxHighlighting();

        try {
            Image downloadImage = new Image(getClass().getResourceAsStream("/images/download.png"));
            ImageView downloadIconView = new ImageView(downloadImage);
            downloadIconView.setFitWidth(24);
            downloadIconView.setFitHeight(24);
            downloadIconView.setPreserveRatio(true);
            saveButton.setGraphic(downloadIconView);

            Image runImage = new Image(getClass().getResourceAsStream("/images/run.png"));
            ImageView runIconView = new ImageView(runImage);
            runIconView.setFitWidth(24);
            runIconView.setFitHeight(24);
            runIconView.setPreserveRatio(true);
            analyzeButton.setGraphic(runIconView);

            previewIcon.setImage(new Image(getClass().getResourceAsStream("/images/preview.png")));
            menuIcon.setImage(new Image(getClass().getResourceAsStream("/images/config.png")));
            inputIcon.setImage(new Image(getClass().getResourceAsStream("/images/inputIcon.png")));
        } catch (Exception e) {
            System.err.println("No se pudo cargar una o más imágenes: " + e.getMessage());
        }

        previewPane.setVisible(false);
        previewPane.setManaged(false);
    }

    private void applySyntaxHighlighting() {
        Pattern pattern = Pattern.compile(
                "(?<KEYWORD>\\b(page|section|text|button|image|width|height|color|background|font-size|margin|padding|with|doc|enddoc|if|else|for|while|return|function|var|let|const)\\b)"
                + "|(?<NUMBER>\\b\\d+(\\.\\d+)?\\b)"
                + "|(?<STRING>\"([^\"\\\\]|\\\\.)*\"|'([^'\\\\]|\\\\.)*')"
                + "|(?<ID>\\b[a-zA-Z_][a-zA-Z0-9_-]*\\b)"
                + "|(?<OPERATOR>\\+|\\-|\\*|\\/|\\=|\\+\\+|\\-\\-|\\!=|\\==|\\&\\&|\\|\\|)"
                + "|(?<BRACE>[{}=])"
                + "|(?<COMMENT>//[^\n]*)"
        );

        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, computeHighlighting(newText, pattern));
        });
    }

    private StyleSpans<Collection<String>> computeHighlighting(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                    matcher.group("NUMBER") != null ? "number" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("ID") != null ? "id" :
                    matcher.group("OPERATOR") != null ? "operator" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("COMMENT") != null ? "comment" :
                    null;

            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}