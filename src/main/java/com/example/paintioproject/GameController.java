package com.example.paintioproject;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;


public class GameController extends GameThings {
   private Parent root;
   private Stage stage;
   private Scene GameOverscene;

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
    public Thread BulletThread;
     private Bullets BulletB;
     private int row_move = 0;
    private int column_move = 0;
    private int speed;
    private int  BulletMAG = 2;
    private PlayerData playerdata;
    private KeyEvent lastKeyEvent;
    private KeyEvent HelpKeyEvent;
    private Long LastShootAttempt = 0L;
    private static final long COOLDOWN = 3000;
    private MediaPlayer mediaplayer;
    private MediaPlayer SoundEffectPlayer;

    private File file = new File();
    private boolean AmiDead =false;

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
                            MediaPlayerManager.loadSoundEffect(file.BelletAEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    lastKeyEvent = HelpKeyEvent;
                }
                case ESCAPE -> {

                }
                case ENTER -> {
                    long CurrentTime = System.currentTimeMillis();
                        if(CurrentTime - LastShootAttempt >= COOLDOWN) {
                            LastShootAttempt = CurrentTime;
                            BulletB.SetBullet(HelpKeyEvent, row_move + 12, column_move + 12, "PLAYER1");
                            BulletB.SetShootBullet(true);
                            MediaPlayerManager.loadSoundEffect(file.BelletBEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
                            //  BulletThread.start();
                            // player.SetNodes(gp, row_move, column_move, player.GetPlayerColor(), player.GetTraceColor());
                        }else{
                            System.out.println("!COOLDOWN!");
                        }
                    lastKeyEvent = HelpKeyEvent;


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
    AnimationTimer BulletLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {

        }
    };
    void handleKeyPress(KeyEvent event) throws InterruptedException {
        lastKeyEvent = event;
    }
    public void firstnodehandel (Color playercolor) {
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
        gameOverController.Setplayerdata(playerdata);
        gameOverController.SetScoreLabel(player.GetPlayerScore());
        GameOverscene = new Scene(root);
        stage = (Stage)Pane.getScene().getWindow();
        stage.setScene(GameOverscene);

    }
    public void SetBullet(Bullets Bullet){
        BulletB = Bullet;
       BulletThread = new Thread(BulletB);
       BulletThread.start();
    }
    public void Setplayerdata(PlayerData playerdata ){
        System.out.println(playerdata.getUsername());
        this.playerdata = playerdata;
    }
    public void SetMediaPlayer(MediaPlayer mediaplayer){
        this.mediaplayer = mediaplayer;
        Random Rand = new Random();
        int random = Rand.nextInt(3);
        switch (random){
            case(0) ->{
                MediaPlayerManager.StopPlaying();
                MediaPlayerManager.loadMedia(file.Continue);
                mediaplayer = MediaPlayerManager.getMediaPlayer();
                mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaplayer.play();

            }
            case(1) ->{
                MediaPlayerManager.StopPlaying();
                MediaPlayerManager.loadMedia(file.UpperCut);
                mediaplayer = MediaPlayerManager.getMediaPlayer();
                mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaplayer.play();
            }
            case(2) -> {
                MediaPlayerManager.StopPlaying();
                MediaPlayerManager.loadMedia(file.Shoryuken);
                mediaplayer = MediaPlayerManager.getMediaPlayer();
                mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaplayer.play();
            }
        }
    }
}



