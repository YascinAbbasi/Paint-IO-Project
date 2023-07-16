package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GameModeController {
    private Parent root;
    private Parent GamePage;
    private Scene scene;
    private Stage stage;
    @FXML
    public ImageView GameModeImageView;

    public void BackButton(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void StartButton(ActionEvent e1)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GamePage.fxml"));
        GamePage = loader.load();
        stage =  (Stage) ((Node) e1.getSource()).getScene().getWindow();
        scene = new Scene(GamePage);
        GameController controller = loader.getController();
        controller.nodehandel();
        scene.setOnKeyPressed(event -> {
            try {
                controller.handleKeyPress(event);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        stage.setScene(scene);
    }

}
