package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameModeController implements Initializable {
    private Parent root;
    private Parent GamePage;
    private Scene scene;
    private Stage stage;
    private Color playercolor;
    @FXML
    public ImageView GameModeImageView;
    @FXML
    private ChoiceBox<String> ColorChoiceBox;
    @FXML
    private Label ErrorLabel;
    private String[] Colors ={"RED","BLUE","GREEN","PURPLE"};

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
        GameController gamecontroller = loader.getController();
        gamecontroller.nodehandel();
        gamecontroller.SetPlayerColor(Getplayercolor());
        gamecontroller.SetDefaultArea();
        gamecontroller.AddPlayerID(3);
        scene.setOnKeyPressed(event -> {
            try {
                gamecontroller.handleKeyPress(event);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        stage.setScene(scene);
    }
    public void setplayercolor(ActionEvent event){
        switch(ColorChoiceBox.getValue()){
            case("RED"):
                playercolor = Color.RED;
            break;

            case("BLUE"):
                playercolor = Color.BLUE;
                break;

            case ("GREEN"):
                playercolor = Color.GREEN;
                break;

            case("PURPLE"):
                playercolor = Color.PURPLE;
                break;
        }
    }
    public Color Getplayercolor(){
        return playercolor;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColorChoiceBox.getItems().addAll(Colors);
        ColorChoiceBox.setOnAction(this::setplayercolor);
    }
}
