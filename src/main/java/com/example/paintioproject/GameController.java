package com.example.paintioproject;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameController extends GameThings {
   private Parent root;
   private Stage stage;
   private Scene GameOverscene;
    private Color PlayerColor;
    private Color TraceColor;
    private Player player;
    private BotManager botplayer;
    private BotManager botplayer2;
    private BotManager botplayer3;
    @FXML
    private Parent Pane;
    @FXML
    private Rectangle RECT;
    @FXML
    private Label label = new Label("\uD83C\uDFAE");
    @FXML
    private GridPane gp;
    private Circle Bullet = new Circle(10,Color.LIGHTYELLOW);
     public Thread thread;
     public Thread thread2;
     public Thread thread3;
     public Thread Bot1;
    public Thread Bot2;
    public Thread Bot3;
     private Bullets BulletB;
     @FXML
     private Button GameOverButton;


    private int row_move = 0;
    private int column_move = 0;
    private int speed;
    private int  BulletMAG = 2;
    private KeyEvent lastKeyEvent;
    private KeyEvent HelpKeyEvent;
    private KeyEvent ShootKeyEvent;
    public static ArrayList<BotManager> BotPlayers =  new ArrayList<>();
     
    private boolean AmiDead =false;
   // Rectangle rect = new Rectangle(25, 25, GetPlayerColor());

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void SetBotPlayer(BotManager Botplayer){
        botplayer = Botplayer;
        thread = new Thread(botplayer);
        thread.start();
    }
    public void SetBotPlayer2(BotManager Botplayer2){
        botplayer2 = Botplayer2;
        thread2 = new Thread(botplayer2);
        thread2.start();
    }
    public void SetBotPlayer3(BotManager Botplayer3){
        botplayer3 = Botplayer3;
        thread3 = new Thread(botplayer3);
        thread3.start();

    }
    AnimationTimer gameLoop = new AnimationTimer() { //BEMON OC
        @Override
        public void handle(long now) {
            switch (lastKeyEvent.getCode()) {
                case LEFT -> {
                    column_move--;
                    player.FindColumnNodes(row_move, column_move, true);
                    HelpKeyEvent = lastKeyEvent;
                }
                case RIGHT -> {
                    column_move++;
                    player.FindColumnNodes(row_move, column_move, false);
                    HelpKeyEvent = lastKeyEvent;
                }
                case UP -> {
                    row_move--;
                    player.FindRowNodes(row_move, column_move, true);
                    HelpKeyEvent = lastKeyEvent;
                }
                case DOWN -> {
                    row_move++;
                    player.FindRowNodes(row_move, column_move, false);
                    HelpKeyEvent = lastKeyEvent;
                }
                case SPACE -> {
                    if(BulletMAG > 0) {
                        try {
                            ShootBulletA(HelpKeyEvent, row_move + 12, column_move + 12, player.GetPlayerColor(), player.GetTraceColor(), "PLAYER1");
                            BulletMAG--;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    lastKeyEvent = HelpKeyEvent;
                }
                case ESCAPE -> {

                }
                case ENTER -> {
                    BulletB = new Bullets(HelpKeyEvent,row_move,column_move,"PLAYER1");
                    //Thread BulletThread = new Thread(BulletB);
                    //BulletThread.start();
                    try {
                        BulletB.ShootBulletB(HelpKeyEvent,row_move,column_move,"PLAYER1");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    HelpKeyEvent = lastKeyEvent;
                }
                default -> {
                    switch (HelpKeyEvent.getCode()){
                        case LEFT -> {
                            column_move--;
                            player.FindColumnNodes(row_move, column_move, true);
                          //  HelpKeyEvent = lastKeyEvent;
                        }
                        case RIGHT -> {
                            column_move++;
                            player.FindColumnNodes(row_move, column_move, false);
                           // HelpKeyEvent = lastKeyEvent;
                        }
                        case UP -> {
                            row_move--;
                            player.FindRowNodes(row_move, column_move, true);
                          //  HelpKeyEvent = lastKeyEvent;
                        }
                        case DOWN -> {
                            row_move++;
                            player.FindRowNodes(row_move, column_move, false);
                            //HelpKeyEvent = lastKeyEvent;
                        }
                    }
                    //lastKeyEvent = HelpKeyEvent;
                }
            }
            player.SetNodes(gp, row_move, column_move, player.GetPlayerColor(), player.GetTraceColor());
            gp.add(label,12,12);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            AmiDead = AmIDead("PLAYER1");
             if(AmiDead){
                 try {
                         SwitchtoGameOverScene();
                         gameLoop.stop();
                 } catch (IOException e) {
                         throw new RuntimeException(e);
                 }
             }

        }
    };
    AnimationTimer BulletLoop = new AnimationTimer() { //BEMON
        @Override
        public void handle(long now) {

        }
    };
    void handleKeyPress(KeyEvent event) throws InterruptedException { //BEMON
        lastKeyEvent = event;
    }
    public void firstnodehandel (Color playercolor) { //IDK FOR NOW
            DefaultNodeGenerator();
            int nodenum = 0;
            for (int i = 0; i <= 24; i++) {
                for (int j = 0; j <= 24; j++) {
                    if(i == 12 & j == 12){
                        nodes.get(nodenum).setColor(playercolor);
                    }
                    gp.add(nodes.get(nodenum), i, j);
                    nodenum++;
                }
            }

            gp.add(rect, 12, 12);
         GridPane.setConstraints(label,12,12);
       //  GridPane.setConstraints(GameOverButton,0,24);


        }
    public void Setspeed(int speed){  //BEMON
        this.speed = speed;

    }
    public void SetLastKeyEvent(KeyEvent lastKeyEvent){
        this.lastKeyEvent = lastKeyEvent;
    }

    public void SwitchtoGameOverScene() throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverPage.fxml"));
        root = loader.load();
        Image image = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai (1)GameOver.jpg");
        GameOverController gameOverController = loader.getController();
        gameOverController.GameOverImageView.setImage(image);
        gameOverController.SetScoreLabel(Integer.toString(player.GetPlayerScore()));
        GameOverscene = new Scene(root);
        stage = (Stage)Pane.getScene().getWindow();
        stage.setScene(GameOverscene);

    }
}



