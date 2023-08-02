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
    private ChoiceBox <String> DifficultyLevelChoiceBox;
    @FXML
    private ChoiceBox <String> BotNumberChoiceBox;
    @FXML
    private Label ErrorLabel;
   /* BotPlayer Bot1 = new BotPlayer();
    BotPlayer Bot2 = new BotPlayer();
    BotPlayer Bot3 = new BotPlayer();
    BotManager BotManager1 = new BotManager(Bot1);
    BotManager BotManager2 = new BotManager(Bot2);
    BotManager BotManager3 = new BotManager(Bot3);*/
    private ArrayList <BotManager> BotManagers = new ArrayList<>();
    private String[] Colors ={"RED","BLUE","GREEN","PURPLE"};
    private String[] Speed = {"SLOW","NORMAL","FAST"};

    private String[] Difficulty = {"EASY","NORMAL","HARD"};
    private String[] BotNumber = {"1","2","3"};
    private String difficulty;
    private BotPlayer bt ;
    private Bullets Bullet = new Bullets();
    private int speed;
    private static int Botnumber = 1;
    public void BackButton(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void StartButton(ActionEvent e1) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("testGamePage.fxml"));
        GamePage = loader.load();
        stage =  (Stage) ((Node) e1.getSource()).getScene().getWindow();
        scene = new Scene(GamePage);
        GameController gamecontroller = loader.getController();
        Player player = new Player();
        BotPlayer Botplayer = new BotPlayer();
        BotPlayer Botplayer2 = new  BotPlayer();
        BotManager botplayer2 = new BotManager(Botplayer2);
        BotManager botplayer = new BotManager(Botplayer);
        gamecontroller.firstnodehandel(Getplayercolor());
        SetPlayer(player);
        switch(Botnumber){
            case(1) -> {
                BotPlayer Bot1 = new BotPlayer();
                BotManager BotManager1 = new BotManager(Bot1);
               SetBot(BotManager1);
               gamecontroller.SetBotPlayer(BotManager1);
            }
            case(2) -> {
                BotPlayer Bot1 = new BotPlayer();
                BotPlayer Bot2 = new BotPlayer();
                BotManager BotManager1 = new BotManager(Bot1);
                BotManager BotManager2 = new BotManager(Bot2);
                SetBot(BotManager1);
                SetBot(BotManager2);
                gamecontroller.SetBotPlayer(BotManager1);
                gamecontroller.SetBotPlayer2(BotManager2);

            }
            case(3) -> {
                BotPlayer Bot1 = new BotPlayer();
                BotPlayer Bot2 = new BotPlayer();
                BotPlayer Bot3 = new BotPlayer();
                BotManager BotManager1 = new BotManager(Bot1);
                BotManager BotManager2 = new BotManager(Bot2);
                BotManager BotManager3 = new BotManager(Bot3);
                SetBot(BotManager1);
                SetBot(BotManager2);
                SetBot(BotManager3);
                gamecontroller.SetBotPlayer(BotManager1);
               gamecontroller.SetBotPlayer2(BotManager2);
                gamecontroller.SetBotPlayer3(BotManager3);

            }
        }
       // player.SetPlayerColor(Getplayercolor());
       /* BotManager.AlreadyTakenColors.add(Getplayercolor());
        botplayer.SetBotColor();
        botplayer.AddPlayerID(3);
        botplayer.SetBotID();*/
       // player.SetDefaultArea(Getplayercolor());
       /* botplayer.SetDefaultBotArea();
        botplayer.SetPlayer();
        botplayer.setSpeed(400);*/
      //  SetBot(botplayer);
       // SetBot(botplayer2);
        gamecontroller.Setspeed(Getspeed());
        gamecontroller.setPlayer(player);
        gamecontroller.SetBullet(Bullet); ////@@@@@@@@@
    //    gamecontroller.SetBotPlayer(botplayer);

      //  gamecontroller.SetBotPlayers(BotManagers);
        //gamecontroller.SetBotPlayer2(botplayer2);
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
        DifficultyLevelChoiceBox.getItems().addAll(Difficulty);
        DifficultyLevelChoiceBox.setOnAction(this::SetDifficulty);
        BotNumberChoiceBox.getItems().addAll(BotNumber);
        BotNumberChoiceBox.setOnAction(this::ChooseBotNumber);
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
    public void SetBot(BotManager botmanager) throws InterruptedException {
        BotManager.AlreadyTakenColors.add(Getplayercolor());
        botmanager.SetBotColor();
        botmanager.AddPlayerID(3);
        botmanager.SetBotID();
        botmanager.SetDefaultBotArea();
       // botmanager.Kill(botmanager.GETbotID());
       // botmanager.ReGenerate(true);
      //  botmanager.SetPlayer();
        botmanager.setDifficulty(difficulty);
        botmanager.setSpeed(200);
         botmanager.Kill(botmanager.GETbotID());
         botmanager.ReGenerate(true);
    }
    public void SetDifficulty(ActionEvent event3){
        switch(DifficultyLevelChoiceBox.getValue()){
            case("EASY"):

                difficulty = "EASY";

            break;

            case("NORMAL"):

                difficulty = "NORMAL";

                break;

            case("HARD"):
                difficulty = "HARD";

                break;
        }
    }
   public void ChooseBotNumber(ActionEvent event4){
        BotManagers.clear();
        switch (BotNumberChoiceBox.getValue()){
            case("1") -> {
               Botnumber = 1;
                /* BotPlayer Bot1 = new BotPlayer();
                BotManager BotManager1 = new BotManager(Bot1);
                SetBot(BotManager1);
                BotManagers.add(BotManager1);*/
            }

            case ("2") -> {
                Botnumber = 2;
              /*  BotPlayer Bot1 = new BotPlayer();
                BotPlayer Bot2 = new BotPlayer();
                BotManager BotManager1 = new BotManager(Bot1);
                BotManager BotManager2 = new BotManager(Bot2);
                SetBot(BotManager1);
                SetBot(BotManager2);
                BotManagers.add(BotManager1);
                BotManagers.add(BotManager2);*/

            }
            case("3") ->{
                Botnumber = 3;
               /* BotPlayer Bot1 = new BotPlayer();
                BotPlayer Bot2 = new BotPlayer();
                BotPlayer Bot3 = new BotPlayer();
                BotManager BotManager1 = new BotManager(Bot1);
                BotManager BotManager2 = new BotManager(Bot2);
                BotManager BotManager3 = new BotManager(Bot3);
                SetBot(BotManager1);
                SetBot(BotManager2);
                SetBot(BotManager3);
                BotManagers.add(BotManager1);
                BotManagers.add(BotManager2);
                BotManagers.add(BotManager3);*/
            }


        }
   }
}
