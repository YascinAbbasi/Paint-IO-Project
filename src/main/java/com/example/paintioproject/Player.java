package com.example.paintioproject;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends GameThings {

    private String PlayerID;
    private Color PlayerColor;
    private Color TraceColor;

    Rectangle rect = new Rectangle(25, 25);// GetPlayerColor());
    private Label label = new Label("  \uD83C\uDFAE  ");
    public void SetNodes(GridPane gp, int row_move, int column_move, Color PlayerColor, Color TraceColor){ //bayad bre to player
        int row = 0;
        int column = 0;
        Rectangle rect = new Rectangle(25, 25,PlayerColor);
        gp.getChildren().clear();

        for(int i = row_move; i <= row_move +24;i++) {
            column = 0;
            for (int k = column_move; k <= column_move + 24; k++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == i && nodes.get(j).GetColumn() == k){
                        gp.add(nodes.get(j),column++,row);
                        if((row == 12 && column   == 13 )&& (!nodes.get(j).GetIs_colored())){
                            nodes.get(j).setColor(TraceColor); //RANG
                            nodes.get(j).SetIs_passed(true);
                            Owner.put(nodes.get(j),"PLAYER1");
                        }
                        if((row == 12 && column   == 13 )&& (nodes.get(j).GetIs_colored()) && (!Owner.containsKey(nodes.get(j)) ||
                                Owner.get(nodes.get(j)) == "PLAYER1")){
                            color_the_path(PlayerColor);
                        }
                    }
                }
            }
            row++;
        }
        gp.add(rect,12,12);
        //gp.add(label,12,12);
    }

   /* public void nodehandel (GridPane gp) { //IDK FOR NOW
        DefaultNodeGenerator();
        int nodenum = 0;
        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j <= 24; j++) {
                if(i == 12 & j == 12){
                    nodes.get(nodenum).setColor(PlayerColor); //RANG
                }
                gp.add(nodes.get(nodenum), i, j);
                nodenum++;
            }
        }

        gp.add(rect, 12, 12);
        GridPane.setConstraints(label,12,12);


    }*/
    public void SetDefaultArea(Color color) {
        for (int m = 11; m <= 13; m++) {
            for (int n = 11;n <=13;n++){
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == m && nodes.get(j).GetColumn() == n) {
                        nodes.get(j).setColor(color); //RANG
                        nodes.get(j).SetIs_colored(true);
                        Owner.put(nodes.get(j),"PLAYER1");
                        break;
                    }
                }
            }
        }
    }

    public void SetPlayerColor(Color PlayerColor){ //BRO I GUESS //RANG
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
    public Color GetPlayerColor(){
        return PlayerColor;
    }

    public Color GetTraceColor() {
        return TraceColor;
    }
}
