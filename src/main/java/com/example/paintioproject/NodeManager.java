package com.example.paintioproject;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NodeManager {


    private boolean is_grey = false;
    private boolean is_exists = false;
    private boolean ID_in_use = false;
    private boolean Color_in_use = false;

    public ArrayList<node> nodes = new ArrayList<>();
    public ArrayList<node> tempNodes = new ArrayList<>();
    public HashMap<node,String> Owner = new HashMap<>();
    public ArrayList<String> PlayerID = new ArrayList<>();
    public ArrayList <Color> AlreadyTakenColors = new ArrayList<>();
    public ArrayList <String>  AlreadyTakenIDs = new ArrayList<>();
   // private String BotID;
   /* private Color PlayerColor;
    private Color TraceColor;*/


   /* public void SetPlayerColor(Color PlayerColor){
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
    }*/

   /* public void SetBotID(){
        for(int i = 0; i < PlayerID.size();i++){
            for(int j = 0; j < AlreadyTakenIDs.size();j++){
                if(PlayerID.get(j) == AlreadyTakenIDs.get(i)){
                    ID_in_use = true;
                    break;
                }
                else {
                    ID_in_use = false;
                }
            }
            if(!ID_in_use){
                BotID = PlayerID.get(i);
            }
        }
    }*/
   /* public String GetBotID(){
        return BotID;
    }*/
    public void SetTakenColors(Color color){
        AlreadyTakenColors.add(color);

    }
    public void SetTakenID(String ID){
        AlreadyTakenIDs.add(ID);
    }


    /*public Color GetPlayerColor(){
        return PlayerColor;
    }
    public Color GetTraceColor(){
        return TraceColor;
    }*/

    public void DefaultNodeGenerator() {

        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j <= 24; j++) {
                if ((i + j) % 2 == 0) {
                    node temp = new node(Color.WHITE, i, j, true);
                    nodes.add(temp);
                    System.out.print("W ");
                } else {
                    node temp = new node(Color.LIGHTGREY, i, j, true);
                    nodes.add(temp);
                    System.out.print("B "); // Black square
                }
            }
            System.out.println();
        }
    }

   /* public void AddPlayerID( int playernum){
        for(int i = 2; i <= playernum + 1; i++){
            PlayerID.add("PLAYER" + i);
        }
          System.out.println(PlayerID);
    }*/



    public void RowNodeGenerator(int row_move, int column_move) {
        System.out.println("r gen working");

        if (((row_move % 2 == 0) && (column_move % 2 == 0)) || ((row_move % 2 != 0) && (column_move % 2 != 0))) {
            is_grey = false;
        } else {
            is_grey = true;
        }
        for (int i = column_move; i <= 24 + column_move; i++) {
            if (!is_grey) {
                node temp = new node(Color.WHITE, row_move, i, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = true;
            } else {
                node temp = new node(Color.LIGHTGREY, row_move, i, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = false;
            }
        }

    }

    public void ColumnNodeGenerator(int row_move, int column_move) {
        System.out.println("c gen working");

        if (((row_move % 2 == 0) && (column_move % 2 == 0)) || ((row_move % 2 != 0) && (column_move % 2 != 0))) {
            is_grey = false;
        } else {
            is_grey = true;
        }
        for (int i = row_move; i <= 24 + row_move; i++) {
            if (!is_grey) {
                node temp = new node(Color.WHITE, i, column_move, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = true;
            } else {
                node temp = new node(Color.LIGHTGREY, i, column_move, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = false;
            }
        }
    }

    public void FindRowNodes(int row_move, int column_move, boolean UP ) {
        System.out.println("r find working");
        boolean UPP = UP;
        tempNodes.clear();
        if (!UP) {
            row_move += 24;
        }
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetRow() == row_move) {
                for (int i = column_move; i <= column_move + 24; i++) {
                    if (nodes.get(j).GetColumn() == i) {
                        node temp = nodes.get(j);
                        tempNodes.add(temp);
                       // System.out.println("temp check:");
                       // System.out.println(tempNodes.size());
                    }
                }
            }
           // System.out.println("f check temp:");
           // System.out.println(tempNodes.size());
        }
        if (tempNodes.size() == 0) {
            System.out.println("Called!");
            RowNodeGenerator(row_move, column_move);
        } else {
            for (int i = column_move; i <= column_move + 24; i++) {
                for (int j = 0; j < tempNodes.size(); j++) {
                    if (tempNodes.get(j).GetColumn() == i) {
                        is_exists = true;
                        break;
                    }else{
                        is_exists = false;
                    }
                }
                if (!is_exists) {
                    if (((row_move % 2 == 0) && (i % 2 == 0)) || ((row_move % 2 != 0) && (i % 2 != 0))) {
                        is_grey = false;
                    } else {
                        is_grey = true;
                    }
                    if (is_grey) {
                        node temp = new node(Color.LIGHTGREY, row_move, i, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    } else {
                        node temp = new node(Color.WHITE, row_move, i, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    }

                }
            }
        }

        System.out.println(nodes.size());

    }
   /* public void SetDefaultArea() {
        for (int m = 11; m <= 13; m++) {
            for (int n = 11;n <=13;n++){
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == m && nodes.get(j).GetColumn() == n) {
                        nodes.get(j).setColor(Get);
                        nodes.get(j).SetIs_colored(true);
                        Owner.put(nodes.get(j),"PLAYER1");
                        break;
                    }
                }
            }
        }
    }*/


    public void FindColumnNodes(int row_move, int column_move, boolean LEFT) {
        System.out.println("c find working");
        tempNodes.clear();
        if (!LEFT) {
            column_move += 24;
        }
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetColumn() == column_move) {
                for (int i = row_move; i <= row_move + 24; i++) {
                    if (nodes.get(j).GetRow() == i) {
                        node temp = nodes.get(j);
                        tempNodes.add(temp);
                    }
                }
            }
        }
        if (tempNodes.size() == 0) {
            System.out.println("Called!");
            ColumnNodeGenerator(row_move, column_move);
        } else {
            for (int i = row_move; i <= row_move + 24; i++) {
                for (int j = 0; j < tempNodes.size(); j++) {
                    if (tempNodes.get(j).GetRow() == i) {
                        is_exists = true;
                        break;
                    }else{
                        is_exists = false;
                    }
                }
                if(!is_exists){
                    if(((column_move % 2 == 0) && (i % 2 == 0)) || (( column_move % 2 != 0 ) && (i % 2 != 0))){
                        is_grey = false;
                    }else {
                        is_grey = true;
                    }
                    if(is_grey) {
                        node temp = new node(Color.LIGHTGREY, i, column_move, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    }else{
                        node temp = new node(Color.WHITE, i, column_move, false);
                        nodes.add(temp);
                        tempNodes.add(temp);
                    }

                }
            }
        }
        System.out.println(nodes.size());

    }

   /* public GridPane SetNodes(GridPane gp,int row_move,int column_move){
        int row = 0;
        int column = 0;
        gp.getChildren().clear();

        for(int i = row_move; i <= row_move +24;i++) {
             column = 0;
            for (int k = column_move; k <= column_move + 24; k++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).row == i && nodes.get(j).column == k){
                        gp.add(nodes.get(j),column++,row);
                        if(row == 12 && column   == 13){
                            nodes.get(j).setColor(Color.PINK);
                        }
                    }
                }
            }
            row++;
        }
        gp.add(rect,12,12);
        return gp;
    }*/
    }




