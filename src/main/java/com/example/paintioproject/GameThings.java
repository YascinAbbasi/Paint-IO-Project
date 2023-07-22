package com.example.paintioproject;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

public class GameThings {
    public static ArrayList<node> nodes = new ArrayList<>();
    public ArrayList<node> tempNodes = new ArrayList<>();
    public static HashMap<node,String> Owner = new HashMap<>();
    public static ArrayList <node> toCheck = new ArrayList<>();
    public static ArrayList <node> toColor = new ArrayList<>();
    private node tempnode;
    private node Helptempnode;
    Rectangle rect = new Rectangle(25, 25);// GetPlayerColor());
   //private Label label;

    private boolean is_grey = false;
    private boolean is_exists = false;
    private boolean is_found =false;
    private int MaxRow = -1000000;
    private int MinRow = 1000000;
    private int MaxColumn = -1000000;
    private int MinColumn = 1000000;
    private int TEMP = 0;


    public void DefaultNodeGenerator() {
        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j <= 24; j++) {
                node temp;
                if ((i + j) % 2 == 0) {
                    temp = new node(Color.WHITE, i, j, true);
                } else {
                    temp = new node(Color.LIGHTGREY, i, j, true);
                }
                nodes.add(temp);
            }
            }
        }


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

    public synchronized void FindRowNodes(int row_move, int column_move, boolean UP ) {
        System.out.println("r find working");
        //boolean UPP = UP;
        tempNodes.clear();
        if (!UP) {
            row_move += 24;
        }
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).GetRow() == row_move) {
                for (int i = column_move; i <= column_move + 24; i++) {
                    if (nodes.get(j).GetColumn() == i) {
                        node temp = nodes.get(j);
                        tempNodes.add(nodes.get(j));

                    }
                }
            }

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



    public synchronized void FindColumnNodes(int row_move, int column_move, boolean LEFT) {
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
                        tempNodes.add(nodes.get(j));
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

    public void SetNodes(GridPane gp, int row_move, int column_move,Color PlayerColor, Color TraceColor){ //bayad bre to player
        int row = 0;
        int column = 0;
        gp.getChildren().clear();
        //label.setText("\uD83C\uDFAE");

        for(int i = row_move; i <= row_move +24;i++) {
            column = 0;
            for (int k = column_move; k <= column_move + 24; k++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == i && nodes.get(j).GetColumn() == k){
                        gp.add(nodes.get(j),column++,row);
                        if((row == 12 && column   == 13 )&& (!nodes.get(j).GetIs_colored())){
                            nodes.get(j).setColor(TraceColor);
                            nodes.get(j).SetIs_passed(true);
                            Owner.put(nodes.get(j),"PLAYER1");
                        }
                        if((row == 12 && column   == 13 )&& (nodes.get(j).GetIs_colored()) && (!Owner.containsKey(nodes.get(j)) ||
                                Owner.get(nodes.get(j)) == "PLAYER1")){
                          //  color_the_path();
                        }
                    }
                }
            }
            row++;
        }
        gp.add(rect,12,12);
      // gp.add(label,12,12);
    }

    public synchronized void color_the_path(Color color,String ID){
        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed() && !nodes.get(j).GetIs_colored() && (Owner.get(nodes.get(j))
             == ID || !Owner.containsKey(nodes.get(j)))){
                nodes.get(j).setColor(color);
                nodes.get(j).SetIs_colored(true);
                nodes.get(j).SetIs_passed(false);
            }
        }
    }
    public synchronized  void ColorTheArea(Color color,String ID){
        toColor.clear();



        for(int j = 0; j < nodes.size(); j++){
            if(nodes.get(j).GetIs_passed()){
                if(Owner.get(nodes.get(j)) == ID){
                    if(nodes.get(j).GetRow() > MaxRow){
                        MaxRow = nodes.get(j).GetRow();
                    }else if(nodes.get(j).GetRow() < MinRow){
                        MinRow = nodes.get(j).GetRow();
                    } if( nodes.get(j).GetColumn() > MaxColumn){
                        MaxColumn = nodes.get(j).GetColumn();
                    }else if(nodes.get(j).GetColumn() < MinColumn){
                        MinColumn = nodes.get(j).GetColumn();
                    }
                }
            }
        }
        for(int i = MinRow; i <= MaxRow; i++){
            for(int j = MinColumn; j <= MaxColumn;j++){
                for(int n = 0; n < nodes.size();n++){
                    if(nodes.get(n).GetRow() == i && nodes.get(n).GetColumn() == j){
                       toColor.add(nodes.get(n));
                    }
                }
            }
        }
        while(MinRow == MaxRow &&  MinColumn == MaxColumn){

            for(int TEMP =  MinColumn; TEMP <= MaxColumn;TEMP++){ //UP
                tempnode = FindTemp(MinRow,TEMP);
                if(!tempnode.GetIs_passed()){
                   is_found = Checkneighbour(MinRow - 1,TEMP);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                    toCheckArea(tempnode,"UP");
                    }
                }
            }
            for(int TEMP = MaxRow; TEMP >= MinRow; TEMP--){ //RIGHT
                tempnode = FindTemp(TEMP,MaxColumn);
                if(!tempnode.GetIs_passed()){
                    is_found = Checkneighbour(TEMP,MaxColumn + 1);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                        toCheckArea(tempnode,"RIGHT");
                    }

                }
            }
            for(int TEMP = MaxColumn; TEMP >= MinColumn;TEMP--){ // DOWN
                tempnode = FindTemp(MaxRow,TEMP);
                if(!tempnode.GetIs_passed()){
                    is_found = Checkneighbour(MaxRow + 1,TEMP);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                        toCheckArea(tempnode,"DOWN");
                    }
                }
            }
            for(int TEMP = MinRow; TEMP <= MaxRow; TEMP++){   //LEFT
                tempnode = FindTemp(TEMP,MinColumn);
                if(!tempnode.GetIs_passed()){
                    is_found = Checkneighbour(TEMP,MinColumn - 1);
                    if(!is_found){
                        FindtoRemove(tempnode);
                    }else{
                        toCheckArea(tempnode,"LEFT");
                    }
                }
            }
            MinRow++;
            MinColumn++;
            MaxRow--;
            MaxColumn--;
        }



    }
    public node FindTemp(int row, int column){
                tempnode = null;
        for(int j = 0; j < toColor.size();j++){
            if(toColor.get(j).GetRow() == row && toColor.get(j).GetColumn() == column){
                tempnode = toColor.get(j);
                break;
            }
        }
        return tempnode;
    }
    public boolean Checkneighbour(int row, int column){
        is_found = false;
        for(int j = 0 ; j < toColor.size(); j++){
            if(toColor.get(j).GetRow() == row && toColor.get(j).GetColumn() == column){
                is_found = true;
                break;
            }
        }
        return is_found;
    }

    public void FindtoRemove(node Tempnode){
        for(int j = 0; j < toColor.size();j++){
            if(toColor.get(j).equals(Tempnode)){
                toColor.remove(j);
                break;
            }
        }
    }
    public void toCheckArea(node Tempnode, String Direction){
        toCheck.clear();
        toCheck.add(Tempnode);
        Helptempnode = null;
        switch (Direction){
            case("UP"):
                for(int i = Tempnode.GetColumn() + 1; i<= MaxColumn;i++){
                     Helptempnode = FindTemp(MinRow,i);
                     toCheck.add(Helptempnode);
                      is_found = Checkneighbour(MinRow - 1, i);
                      if(Helptempnode.GetIs_passed()){
                          SetTEMP(i);
                          break;
                      }
                      if(!is_found){
                          CleartoCheck();
                          SetTEMP(i + 1);
                          break;
                      }
                }
                break;

            case("RIGHT"):
                for(int i = Tempnode.GetRow() + 1; i<= MaxRow;i++){
                    Helptempnode = FindTemp(i,MaxColumn);
                    toCheck.add(Helptempnode);
                    is_found = Checkneighbour(i, MaxColumn + 1);
                    if(Helptempnode.GetIs_passed()){
                        SetTEMP(i);
                        break;
                    }
                    if(!is_found){
                        CleartoCheck();
                        SetTEMP(i + 1);
                        break;
                    }
                }

                break;

            case("DOWN"):
                for(int i = Tempnode.GetColumn() - 1; i>= MinColumn;i--){
                    Helptempnode = FindTemp(MaxRow,i);
                    toCheck.add(Helptempnode);
                    is_found = Checkneighbour(MaxRow + 1,i);
                    if(Helptempnode.GetIs_passed()){
                        SetTEMP(i);
                        break;
                    }
                    if(!is_found){
                        CleartoCheck();
                        SetTEMP(i - 1);
                        break;
                    }
                }
                break;

            case("LEFT"):
                for(int i = Tempnode.GetRow() - 1; i>= MinRow;i--){
                    Helptempnode = FindTemp(i,MinColumn);
                    toCheck.add(Helptempnode);
                    is_found = Checkneighbour(i, MinColumn - 1);
                    if(Helptempnode.GetIs_passed()){
                        SetTEMP(i);
                        break;
                    }
                    if(!is_found){
                        CleartoCheck();
                        SetTEMP(i - 1);
                        break;
                    }
                }
                break;
            default:
                break;
        }
    }
    public void CleartoCheck(){
         for(int i = 0; i < toColor.size(); i++){
             for(int j = 0; j < toCheck.size();j++){
                 if(toColor.get(i) == toCheck.get(j)){
                     toColor.remove(i);
                 }
             }
         }
    }
    public void SetTEMP(int TEMP){
        this.TEMP = TEMP;
    }


}
