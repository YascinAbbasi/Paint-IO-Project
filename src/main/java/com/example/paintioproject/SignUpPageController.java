package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPageController implements Serializable {
    private static final long serialVersionUID = 1L;
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label WelcomeLabel;
    @FXML
    public ImageView imageview;

    public void BackButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        stage= (Stage) root.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }


}
