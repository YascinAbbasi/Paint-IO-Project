package com.example.paintioproject;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController extends GameThings {
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Color PlayerColor;
    private Color TraceColor;
    private Player player;
    private BotManager botplayer;
    private BotManager botplayer2;
    @FXML
    private Rectangle RECT;
    @FXML
    private Label label = new Label("\uD83C\uDFAE");
    @FXML
    private GridPane gp;
     public Thread thread;
     public Thread thread2;
     private Bullets BulletB;
     @FXML
     private Button GameOverButton;


    private int row_move = 0;
    private int column_move = 0;
    private int speed;
    private KeyEvent lastKeyEvent;
    private KeyEvent HelpKeyEvent;
    private KeyEvent ShootKeyEvent;
     
    private boolean AmiDead =false;
   // Rectangle rect = new Rectangle(25, 25, GetPlayerColor());

    public void setPlayer(Player player) {
        this.player = player;
    }
    public void SetBotPlayer(BotManager botplayer){
        this.botplayer = botplayer;
        thread = new Thread(botplayer);
        thread.start();
    }
    public void SetBotPlayer2(BotManager botplayer2){
        this.botplayer2 = botplayer2;
        thread2 = new Thread(botplayer2);
        thread2.start();
    }




  /* public void AddBot(BotPlayer botPlayer){ // BEMON
        bt = botPlayer;
    } */


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
                    try {
                        ShootBulletA(HelpKeyEvent,row_move + 12,column_move + 12,player.GetPlayerColor(),player.GetTraceColor(),"PLAYER1");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    lastKeyEvent = HelpKeyEvent;
                }
                case ESCAPE -> {

                }
                case ENTER -> {
                    BulletB = new Bullets(HelpKeyEvent,row_move,column_move,"PLAYER1");
                    Thread BulletThread = new Thread(BulletB);
                    BulletThread.start();
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
            AmiDead = AmIDead("PLAYER1");
            if(AmiDead){
                GameOverButton.fire();
            }
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    };
    AnimationTimer BotGameLoop = new AnimationTimer() { //BEMON
        @Override
        public void handle(long now) {

        }
    };


    void handleKeyPress(KeyEvent event) throws InterruptedException { //BEMON
        lastKeyEvent = event;
        //gameLoop.start();
          /*  switch (event.getCode()) {
                case LEFT -> {
                   // column_move--;
                    System.out.println("c move:");
                    System.out.println(column_move);
                    System.out.println("r move:");
                    System.out.println(row_move);
                    FindColumnNodes(row_move, column_move, true);
                    if (row_move == 0 && column_move == 0){
                        //color_the_path(row_move,column_move);
                    }
                    //SetNodes( row_move, column_move);
                    //Thread.sleep(1000);
                    // GridPane.setConstraints(rect, 12, 12);
                }


                case RIGHT -> {

                  //  column_move++;
                    System.out.println("c move:");
                    System.out.println(column_move);
                    System.out.println("r move:");
                    System.out.println(row_move);
                    FindColumnNodes(row_move, column_move, false);
                    if (row_move == 0 && column_move == 0){
                        color_the_path(row_move,column_move);
                    }
                   //SetNodes( row_move, column_move);
                    // GridPane.setConstraints(rect, 12, 12);
                    //Thread.sleep(1000);


                }
                case UP -> {

                   // row_move--;
                    System.out.println("c move:");
                    System.out.println(column_move);
                    System.out.println("r move:");
                    System.out.println(row_move);

                    FindRowNodes(row_move, column_move, true);
                    if (row_move == 0 && column_move == 0){
                        //color_the_path(row_move,column_move);
                    }
                  // SetNodes(row_move, column_move);
                    // GridPane.setConstraints(rect, 12, 12);
                   // Thread.sleep(1000);


                }
                case DOWN -> {

                  //  row_move++;
                    System.out.println("c move:");
                    System.out.println(column_move);
                    System.out.println("r move:");
                    System.out.println(row_move);

                    FindRowNodes(row_move, column_move, false);
                    if (row_move == 0 && column_move == 0){
                       // color_the_path(row_move,column_move);
                    }
                  // SetNodes( row_move, column_move);
                    //  GridPane.setConstraints(rect, 12, 12);
                  // Thread.sleep(1000);


                }
                default -> {
                }

            }
            //Thread.sleep(500);
          // SetNodes( row_move, column_move);
            //GridPane.setConstraints(rect, 12, 12);

         */
    }

   /* public void SetPlayerColor(Color PlayerColor) { //BRO I GUESS
        this.PlayerColor = PlayerColor;
        if (PlayerColor == Color.RED) {
            TraceColor = Color.CORAL;
        } else if (PlayerColor == Color.BLUE) {
            TraceColor = Color.LIGHTBLUE;

        } else if (PlayerColor == Color.GREEN) {
            TraceColor = Color.LIGHTGREEN;
        } else if (PlayerColor == Color.PURPLE) {
            TraceColor = Color.LAVENDER;
        }
    }*/

   /* public void SetDefaultArea() { //BRO
        for (int m = 11; m <= 13; m++) {
            for (int n = 11; n <= 13; n++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == m && nodes.get(j).GetColumn() == n) {
                        nodes.get(j).setColor(PlayerColor);
                        nodes.get(j).SetIs_colored(true);
                        Owner.put(nodes.get(j), "PLAYER1");
                        break;
                    }
                }
            }
        }
    } */

   /* public void AddPlayerID( int playernum){
        for(int i = 2; i <= playernum + 1; i++){
            PlayerID.add("PLAYER" + i);
        }
        System.out.println(PlayerID);
    }
    public Color GetPlayerColor(){
        return PlayerColor;
    }
    public Color GetTraceColor(){
        return TraceColor;
    }*/



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


        }


        /*

    public void SetNodes(int row_move,int column_move){ //BRO
        int row = 0;
        int column = 0;
        gp.getChildren().clear();

        for(int i = row_move; i <= row_move +24;i++) {
            column = 0;
            for (int k = column_move; k <= column_move + 24; k++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == i && nodes.get(j).GetColumn() == k){
                        gp.add(nodes.get(j),column++,row);
                        if((row == 12 && column   == 13 )&& (!nodes.get(j).GetIs_colored())){
                            nodes.get(j).setColor(GetTraceColor());
                            nodes.get(j).SetIs_passed(true);
                            Owner.put(nodes.get(j),"PLAYER1");
                        }
                        if((row == 12 && column   == 13 )&& (nodes.get(j).GetIs_colored()) && (!Owner.containsKey(nodes.get(j)) ||
                        Owner.get(nodes.get(j)) == "PLAYER1")){
                            color_the_path();
                        }
                    }
                }
            }
            row++;
        }
        gp.add(rect,12,12);
        gp.add(label,12,12);
    }

    public void color_the_path(){  //BRO
        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed() && !nodes.get(j).GetIs_colored()){
                nodes.get(j).setColor(GetPlayerColor());
                nodes.get(j).SetIs_colored(true);
                nodes.get(j).SetIs_passed(false);
            }
        }
    }*/
    public void Setspeed(int speed){  //BEMON
        this.speed = speed;

    }
    public void SetLastKeyEvent(KeyEvent lastKeyEvent){
        this.lastKeyEvent = lastKeyEvent;
    }

    public void SwitchtoGameOverScene(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverPage.fxml"));
        root = loader.load();
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}



