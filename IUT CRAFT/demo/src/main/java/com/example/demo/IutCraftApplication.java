package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IutCraftApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(IutCraftApplication.class.getResource("menu.fxml"));
        Musique musique = new Musique("demo/src/main/resources/com/example/demo/Musique.wav");
        musique.start();
        Scene scene = new Scene(fxmlLoader.load(), 710, 600);
        scene.getStylesheets().add(String.valueOf(this.getClass().getResource("style.css")));
        stage.setTitle("IUT CRAFT");
        stage.setScene(scene);
        stage.show();

    }

    public static void main() {
        launch();
    }
}