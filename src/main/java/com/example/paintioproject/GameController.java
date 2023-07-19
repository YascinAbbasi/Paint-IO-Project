package com.example.paintioproject;

import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameController extends NodeManager {
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Color PlayerColor;
    private Color TraceColor;
    private BotPlayer bt;

    @FXML
    private Rectangle RECT;
    @FXML
    private Label label;
    @FXML
    private GridPane gp;

    private int row_move = 0;
    private int column_move = 0;
    private int speed;
    private KeyEvent lastKeyEvent;
    Rectangle rect = new Rectangle(25, 25, GetPlayerColor());

    public void AddBot(BotPlayer botPlayer){
        bt = botPlayer;
    }


    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (lastKeyEvent.getCode() == KeyCode.LEFT){
                column_move--;
                FindColumnNodes(row_move, column_move, true);
            }
            else if(lastKeyEvent.getCode() == KeyCode.RIGHT) {
                column_move++;
                FindColumnNodes(row_move, column_move, false);
            }
            else if(lastKeyEvent.getCode() == KeyCode.UP){
                row_move --;
                FindRowNodes(row_move, column_move, true);
            }
            else if (lastKeyEvent.getCode() == KeyCode.DOWN){
                row_move ++;
                FindRowNodes(row_move, column_move, false);
            }
            else if (lastKeyEvent.getCode() == KeyCode.SPACE){
                System.out.println(bt.test());
            }
           // if (row_move == 0 && column_move == 0){
            //    color_the_path();
          //  }

            SetNodes(row_move, column_move);
          // FindColumnNodes(row_move, column_move, false);
            try {
                 Thread.sleep(speed);
              } catch (InterruptedException e) {
                 throw new RuntimeException(e);
              }

    }
    };




    void handleKeyPress(KeyEvent event) throws InterruptedException {
        gameLoop.start();

            lastKeyEvent = event;
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
    public void SetPlayerColor(Color PlayerColor){
        this.PlayerColor = PlayerColor;
        if(PlayerColor == Color.RED){
            TraceColor = Color.CORAL;
        }else if(PlayerColor == Color.BLUE) {
            TraceColor = Color.LIGHTBLUE;

        }else if(PlayerColor == Color.GREEN){
            TraceColor = Color.LIGHTGREEN;
        }else if(PlayerColor == Color.PURPLE){
            TraceColor = Color.LAVENDER;
        }
    }
    public void SetDefaultArea() {
        for (int m = 11; m <= 13; m++) {
            for (int n = 11;n <=13;n++){
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == m && nodes.get(j).GetColumn() == n) {
                        nodes.get(j).setColor(PlayerColor);
                        nodes.get(j).SetIs_colored(true);
                        Owner.put(nodes.get(j),"PLAYER1");
                        break;
                    }
                }
            }
        }
    }
    public void AddPlayerID( int playernum){
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
    }



        public void nodehandel () {
            DefaultNodeGenerator();
            int nodenum = 0;
            for (int i = 0; i <= 24; i++) {
                for (int j = 0; j <= 24; j++) {
                    if(i == 12 & j == 12){
                        nodes.get(nodenum).setColor(GetPlayerColor());
                    }
                    gp.add(nodes.get(nodenum), i, j);
                    nodenum++;
                }
            }

            gp.add(rect, 12, 12);
         GridPane.setConstraints(label,12,12);


        }

    public void SetNodes(int row_move,int column_move){
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
                        if((row == 12 && column   == 13 )&& (nodes.get(j).GetIs_colored())){
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

    public void color_the_path(){
        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed() && !nodes.get(j).GetIs_colored()){
                nodes.get(j).setColor(GetPlayerColor());
                nodes.get(j).SetIs_colored(true);
                nodes.get(j).SetIs_passed(false);
            }
        }
    }
    public void Setspeed(int speed){
        this.speed = speed;

    }

  }

