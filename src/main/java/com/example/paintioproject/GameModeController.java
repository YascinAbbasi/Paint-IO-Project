package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameModeController implements Initializable {
        //In this class, we set the game requirements such as the player's color, speed, difficulty, and the number of robots
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
    private ArrayList <BotManager> BotManagers = new ArrayList<>();
    private String[] Colors ={"RED","BLUE","GREEN","PURPLE"};
    private String[] Speed = {"SLOW","NORMAL","FAST"};

    private String[] Difficulty = {"EASY","NORMAL","HARD"};
    private String[] BotNumber = {"1","2","3"};
    private String difficulty;
    private Bullets Bullet = new Bullets();
    private PlayerData playerdata;
    private int speed;
    private static int Botspeed = 100;
    private static int Botnumber = 1;
    private  MediaPlayer mediaplayer;
    public void BackButton(ActionEvent e) throws IOException{ //switch to loginpage method
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void StartButton(ActionEvent e1) throws IOException, InterruptedException {
        //This method fills the required information of the game and also handles the switchScene to the game Scene.

        FXMLLoader loader = new FXMLLoader(getClass().getResource("testGamePage.fxml"));
        GamePage = loader.load();
        stage =  (Stage) ((Node) e1.getSource()).getScene().getWindow();
        scene = new Scene(GamePage);
        GameController gamecontroller = loader.getController();
        Player player = new Player();
        gamecontroller.firstnodehandel(Getplayercolor());
        SetPlayer(player);
                                                       //setting the bots
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
                                    //set the player and game requirements
        gamecontroller.Setspeed(Getspeed());
        gamecontroller.setPlayer(player);
        gamecontroller.Setplayerdata(playerdata);
        gamecontroller.SetMediaPlayer(mediaplayer);
        gamecontroller.SetBullet(Bullet);
        RandomDirection();
        gamecontroller.SetLastKeyEvent(lastKeyEvent);
        gamecontroller.gameLoop.start();
        scene.setOnKeyPressed(event -> {

            try {
                gamecontroller.handleKeyPress(event);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        stage.setScene(scene);
    }
    public void setplayercolor(ActionEvent event){ //setting the player's color
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

    public void SetGameSpeed(ActionEvent event2){      // setting the game speed
        switch (SpeedChoiceBox.getValue()){
            case("SLOW"):
                speed = 150;
                SetBotSpeed(150);
                break;

            case("NORMAL"):
                speed = 100;
                SetBotSpeed(100);
                break;

            case("FAST"):
                speed = 50;
                SetBotSpeed(50);
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
    public void  RandomDirection(){     //this method gives a random direction to player at the beginning
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

    public void SetPlayer( Player player){   //setting player
        player.SetPlayerColor(Getplayercolor());
        player.SetDefaultArea(Getplayercolor());
    }
    public void SetBot(BotManager botmanager) throws InterruptedException { //setting bots
        BotManager.AlreadyTakenColors.add(Getplayercolor());
        botmanager.SetBotColor();
        botmanager.AddPlayerID(3);
        botmanager.SetBotID();
        botmanager.SetDefaultBotArea();
        botmanager.setDifficulty(difficulty);
        botmanager.setSpeed(Botspeed);
         botmanager.Kill(botmanager.GETbotID());
         botmanager.ReGenerate(true);
    }
    public void SetDifficulty(ActionEvent event3){   //setting difficulty
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
   public void ChooseBotNumber(ActionEvent event4){  //setting the number of robots
        BotManagers.clear();
        switch (BotNumberChoiceBox.getValue()){
            case("1") -> {
               Botnumber = 1;
            }

            case ("2") -> {
                Botnumber = 2;
            }
            case("3") ->{
                Botnumber = 3;
            }
        }
   }
   public void SetBotSpeed(int Botspeed){
        this.Botspeed = Botspeed;
   }
   public void Setplayerdata(PlayerData playerdata ){   //This part sends the logged-in user object to the game to store their score
        this.playerdata = playerdata;
        System.out.println(playerdata.getUsername());
   }
   public void SetMediaPlayer(MediaPlayer mediaplayer){
       this.mediaplayer = mediaplayer;
   } //sending the media player to the game class
}
