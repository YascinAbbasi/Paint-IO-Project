package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameModeController implements Initializable {
    private Parent root;
    private Parent GamePage;
    private Scene scene;
    private Stage stage;
    private Color playercolor;
    private KeyEvent lastKeyEvent;
    @FXML
    public ImageView GameModeImageView;
    @FXML
    private ChoiceBox<String> ColorChoiceBox;
    @FXML
    private ChoiceBox <String> SpeedChoiceBox;
    @FXML
    private Label ErrorLabel;
    private String[] Colors ={"RED","BLUE","GREEN","PURPLE"};
    private String[] Speed = {"SLOW","NORMAL","FAST"};
    private BotPlayer bt ;
    private int speed;
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
        Player player = new Player();
        BotPlayer Botplayer = new BotPlayer();
        BotManager botplayer = new BotManager(Botplayer);
        gamecontroller.firstnodehandel(Getplayercolor());
        SetPlayer(player);
       // player.SetPlayerColor(Getplayercolor());
       /* BotManager.AlreadyTakenColors.add(Getplayercolor());
        botplayer.SetBotColor();
        botplayer.AddPlayerID(3);
        botplayer.SetBotID();*/
       // player.SetDefaultArea(Getplayercolor());
       /* botplayer.SetDefaultBotArea();
        botplayer.SetPlayer();
        botplayer.setSpeed(400);*/
        SetBot(botplayer);
        gamecontroller.Setspeed(Getspeed());
        gamecontroller.setPlayer(player);
        gamecontroller.SetBotPlayer(botplayer);
        RandomDirection();
        gamecontroller.SetLastKeyEvent(lastKeyEvent);
        gamecontroller.gameLoop.start();
        /*gamecontroller.SetPlayerColor(Getplayercolor());
        gamecontroller.SetTakenColors(Getplayercolor());
        gamecontroller.SetDefaultArea();
        gamecontroller.AddPlayerID(3);
        bt = new BotPlayer();
        gamecontroller.AddBot(bt);*/
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

    public void SetGameSpeed(ActionEvent event2){
        switch (SpeedChoiceBox.getValue()){
            case("SLOW"):
                speed = 200;

                break;

            case("NORMAL"):
                speed = 100;

                break;

            case("FAST"):
                speed = 50;
                break;

        }
    }
    public Color Getplayercolor(){
        return playercolor;
    }
    public int Getspeed(){

        return speed;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ColorChoiceBox.getItems().addAll(Colors);
        ColorChoiceBox.setOnAction(this::setplayercolor);
        SpeedChoiceBox.getItems().addAll(Speed);
        SpeedChoiceBox.setOnAction(this::SetGameSpeed);

    }
    public void  RandomDirection(){
        Random Rand = new Random();
        int rand = Rand.nextInt(4);
        switch(rand){
            case(0):
                KeyEvent keyPressEvent = new KeyEvent(
                        KeyEvent.KEY_PRESSED,  // event type
                        "",                    // character
                        "",                    // text
                        KeyCode.UP,            // key code
                        false,                 // shift down
                        false,                 // control down
                        false,                 // alt down
                        false                  // meta down
                );
                lastKeyEvent = keyPressEvent;
                break;

            case(1):
                 keyPressEvent = new KeyEvent(
                        KeyEvent.KEY_PRESSED,   // event type
                        "",                     // character
                        "",                     // text
                        KeyCode.RIGHT,          // key code
                        false,                  // shift down
                        false,                  // control down
                        false,                  // alt down
                        false                   // meta down
                );
                 lastKeyEvent = keyPressEvent;

                break;

            case(2):
                keyPressEvent = new KeyEvent(
                        KeyEvent.KEY_PRESSED,   // event type
                        "",                     // character
                        "",                     // text
                        KeyCode.DOWN,          // key code
                        false,                  // shift down
                        false,                  // control down
                        false,                  // alt down
                        false                   // meta down
                );
                lastKeyEvent = keyPressEvent;

                break;

            case(3):
                keyPressEvent = new KeyEvent(
                        KeyEvent.KEY_PRESSED,   // event type
                        "",                     // character
                        "",                     // text
                        KeyCode.LEFT,          // key code
                        false,                  // shift down
                        false,                  // control down
                        false,                  // alt down
                        false                   // meta down
                );
                lastKeyEvent = keyPressEvent;
                break;

            default:
                break;
        }
    }

    public void SetPlayer( Player player){
        player.SetPlayerColor(Getplayercolor());
        player.SetDefaultArea(Getplayercolor());
    }
    public void SetBot(BotManager botmanager){
        BotManager.AlreadyTakenColors.add(Getplayercolor());
        botmanager.SetBotColor();
        botmanager.AddPlayerID(3);
        botmanager.SetBotID();
        botmanager.SetDefaultBotArea();
        botmanager.SetPlayer();
        botmanager.setSpeed(400);
    }
}
