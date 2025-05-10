package com.hss.gui.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import javafx.scene.layout.BorderPane;
import org.fxmisc.richtext.LineNumberFactory;
import java.net.URL;
import java.util.ResourceBundle;

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
    private ImageView playIcon;

    @FXML
    private ImageView previewIcon;

    @FXML
    private ImageView menuIcon;

    @FXML
    private ImageView inputIcon;

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

        try {
            playIcon.setImage(new Image(getClass().getResourceAsStream("/images/run.png")));
            previewIcon.setImage(new Image(getClass().getResourceAsStream("/images/preview.png")));
            menuIcon.setImage(new Image(getClass().getResourceAsStream("/images/config.png")));
            inputIcon.setImage(new Image(getClass().getResourceAsStream("/images/inputIcon.png")));
        } catch (Exception e) {
            System.err.println("No se pudo cargar una o más imágenes: " + e.getMessage());
        }
    }
}