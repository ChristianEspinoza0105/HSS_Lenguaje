package com.hss;

import com.hss.gui.controladores.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Interfaz HSS");

        Image fxIcon = new Image(MainApp.class.getResourceAsStream("/images/logo.png"));
        primaryStage.getIcons().add(fxIcon);

        try {
            String os = System.getProperty("os.name").toLowerCase();
            InputStream iconStream = MainApp.class.getResourceAsStream("/images/logo.png");
            if (iconStream != null) {
                BufferedImage bufferedImage = ImageIO.read(iconStream);
                if (bufferedImage != null) {
                    if (os.contains("mac") || os.contains("win")) {
                        Taskbar taskbar = Taskbar.getTaskbar();
                        taskbar.setIconImage(bufferedImage);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar el icono para el sistema: " + e.getMessage());
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "Interfaz HSS");
        launch(args);
    }
}