package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class GameOverController implements Serializable {
    private static final long serialVersionUID = 1L;
    @FXML
    private Label ScoreLabel;
    @FXML
    public ImageView GameOverImageView;
    private Parent root;
    private Scene  scene;
    private Stage  stage;
    private int Score;
    private String PlayerID;

    public void ContinueButton(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        Image image = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai.jpg");
        LoginPageController loginPageController = loader.getController();
        loginPageController.LoginImageView.setImage(image);
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void SetScoreLabel(String score){
        ScoreLabel.setText(score);
    }
}
