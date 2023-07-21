package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class BotPlayer  {

    private Color color;

    private Color BotColor;
    private Color AreaColor;
    private Color TraceColor;
    private boolean ID_in_use = false;
    private boolean Color_in_use = false;
    private boolean is_empty = false;
    private node PlayerNode;
    private ArrayList <node> tempnodes = new ArrayList<>();
     private int row_move = 0;
     private int column_move = 0;

    private String BotID;
   private Color[] Colors = {Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE};

  public BotPlayer(){
     // SetDefaultBotArea();
     // SetPlayer();
  }

    public void SetBotID(String BotID){
      this.BotID = BotID;
    }
    public void SetBotColor(Color BotColor){
      this.BotColor = BotColor;
    }
    public void SetTraceColor(Color TraceColor){
        this.TraceColor = TraceColor;
    }
    public void SetAreaColor (Color AreaColor){
        this.AreaColor = AreaColor;
    }

    public Color GetBOTColor() {
        return BotColor;
    }

    public Color GetAreaColor() {
        return AreaColor;
    }

    public Color GetTraceColor() {
        return TraceColor;
    }


    public String GetBotID(){
        return BotID;
    }

}
