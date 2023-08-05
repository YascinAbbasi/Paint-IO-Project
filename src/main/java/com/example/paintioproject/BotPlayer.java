package com.example.paintioproject;

import javafx.scene.paint.Color;


public class BotPlayer  {
         //This class holds the data of each robot, and we utilize the BotManager class to populate this data.

    private Color BotColor;
    private Color AreaColor;
    private Color TraceColor;

    private String BotID;
   private Color[] Colors = {Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE};

  public BotPlayer(){

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
