package com.example.paintioproject;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class NodeManager {
    private Color grey;
    private Color white;
    private boolean is_grey = false;
    private boolean is_exists = false;

    public ArrayList<node> nodes = new ArrayList<>();
    public ArrayList<node> tempNodes = new ArrayList<>();

    public void DefaultNodeGenerator() {
        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j <= 24; j++) {
                if ((i + j) % 2 == 0) {
                    node temp = new node(Color.WHITE, i, j, true);
                    nodes.add(temp);
                    System.out.print("W ");
                } else {
                    node temp = new node(Color.GREY, i, j, true);
                    nodes.add(temp);
                    System.out.print("B "); // Black square
                }
            }
            System.out.println();
        }
    }

    public void RowNodeGenerator(int row_move, int column_move, boolean UP) {
        if (!UP) {
            row_move += 24;
        }
        if((row_move % 2 == 0) && (column_move % 2 == 0) || ( row_move % 2 != 0 ) && (column_move % 2 != 0)){
            is_grey = false;
        }else {
            is_grey = true;
        }
        for (int i = column_move; i <= 24 + column_move; i++) {
            if (!is_grey) {
                node temp = new node(Color.WHITE, row_move, i, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = true;
            } else {
                node temp = new node(Color.GREY, row_move, i, false);
                nodes.add(temp);
                tempNodes.add(temp);
                is_grey = false;
            }
        }

    }

    public void ColumnNodeGenerator(int row_move, int column_move, boolean LEFT) {
        if (!LEFT) {
            column_move += 24;
        }
        if (column_move % 2 == 0) {
            is_grey = false;
        } else {
            is_grey = true;
        }
        for (int i = row_move; i <= 24 + row_move; i++) {
            if (!is_grey) {
                node temp = new node(Color.WHITE, i, column_move, false);
                nodes.add(temp);
                is_grey = true;
            } else {
                node temp = new node(Color.GREY, i, column_move, false);
                nodes.add(temp);
                is_grey = false;
            }
        }
    }

    public void FindRowNodes(int row_move, int column_move, boolean UP, boolean LEFT) {
        tempNodes.clear();
        if (!UP) {
            row_move += 24;
        }
        for (int j = 0; j < nodes.size(); j++) {
            if (nodes.get(j).row == row_move) {
                for (int i = column_move; i <= column_move + 24; i++) {
                    if (nodes.get(j).column == i) {
                        tempNodes.add(nodes.get(j));
                    }
                }
            }
        }
        if (tempNodes.size() == 0) {
            RowNodeGenerator(row_move, column_move, UP);
        } else {
            for( int i = column_move ; i <= column_move +24; i++){
                for(int j = 0; j < tempNodes.size();j++){
                    if(tempNodes.get(j).column == i){
                        is_exists = true;
                        break;
                    }
                }
                if(!is_exists){

                }
            }
        }

    }
}

