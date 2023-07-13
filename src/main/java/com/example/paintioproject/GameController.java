package com.example.paintioproject;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    @FXML
    private Rectangle RECT;
    @FXML
    private Label label;
    @FXML
    private GridPane gp;
    private int columnIndex = 12;
    private int rowIndex = 12;
    private int row_move = 0;
    private int column_move = 0;
    Rectangle rect = new Rectangle(25,25,Color.RED);


    @FXML
    void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT -> {

                    columnIndex--;
                    column_move--;
                System.out.println("c move:");
                System.out.println(column_move);
                System.out.println("r move:");
                System.out.println(row_move);
                    FindColumnNodes(row_move,column_move,true);
                gp =  SetNodes(gp,row_move,column_move);
                GridPane.setConstraints(rect, 12, 12);



            }
            case RIGHT -> {

                    columnIndex++;
                    column_move++;
                    System.out.println("c move:");
                System.out.println(column_move);
                System.out.println("r move:");
                System.out.println(row_move);


                FindColumnNodes(row_move,column_move,false);
                gp =  SetNodes(gp,row_move,column_move);
                GridPane.setConstraints(rect, 12, 12);




            }
            case UP -> {
                if(rowIndex > 0){
                    rowIndex--;
                    row_move--;
                    System.out.println("c move:");
                    System.out.println(column_move);
                    System.out.println("r move:");
                    System.out.println(row_move);

                    FindRowNodes(row_move,column_move,true);
                    gp =  SetNodes(gp,row_move,column_move);
                    GridPane.setConstraints(rect, 12, 12);

                }

            }
            case DOWN -> {
                if(rowIndex< 24){
                    rowIndex++;
                    row_move++;
                    System.out.println("c move:");
                    System.out.println(column_move);
                    System.out.println("r move:");
                    System.out.println(row_move);

                    FindRowNodes(row_move,column_move,false);
                    gp =  SetNodes(gp,row_move,column_move);
                    GridPane.setConstraints(rect, 12, 12);

                }
            }
            default -> {
            }
        }
        GridPane.setConstraints(rect, 12, 12);

    }
    public void nodehandel(){
       DefaultNodeGenerator();
       int nodenum = 0;
       for(int i = 0; i <= 24;i++){
           for(int j = 0;j <= 24;j++){
               gp.add(nodes.get(nodenum),i,j);
               nodenum++;
           }
       }

       gp.add(rect,12,12);
    }
    public void nodehandel2(){
node nd = new node(Color.PINK,2,2,true);
gp.add(nd,2,2);
node nd2 = new node(Color.GREEN,5,5,true);
//gp.getChildren().remove(nd);
gp.add(nd2,5,5);
    System.out.println(nd2.is_passed);
    }


}
