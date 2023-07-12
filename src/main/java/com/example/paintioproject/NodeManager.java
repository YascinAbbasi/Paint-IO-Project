package com.example.paintioproject;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class NodeManager {
    public ArrayList<node> nodes = new ArrayList<>();
    public void DefaultNodeGenerator(){
        for (int i = 0; i <= 24; i++) {
            for (int j = 0; j <= 24; j++) {
                if ((i + j) % 2 == 0) {
                    node temp = new node(Color.WHITE,i,j,true);
                    nodes.add(temp);
                    System.out.print("W ");
                } else {
                    node temp = new node(Color.GREY,i,j,true);
                    nodes.add(temp);
                    System.out.print("B "); // Black square
                }
            }
            System.out.println();
        }
    }
}
