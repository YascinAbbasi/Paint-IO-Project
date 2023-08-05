package com.example.paintioproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

    public class PaintIoProject extends Application {
        //this is the MainClass
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("file:src/main/resources/images/video-game.png");
        stage.setTitle("Paint IO!");
        stage.getIcons().add(icon);
        stage.setHeight(625);
        stage.setWidth(625);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}