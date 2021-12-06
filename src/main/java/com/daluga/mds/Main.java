package com.daluga.mds;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader);
        stage.setTitle("Sistema MDS");
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/grupoheredia.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}