package com.example.paintioproject;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class BotManager extends GameThings {
    private BotPlayer Bot;
    private Color color;
    private Color BOTColor;
    private Color AreaColor;
    private Color TraceColor;
    private boolean ID_in_use = false;
    private boolean Color_in_use = false;
    private boolean is_empty = false;

    private ArrayList <node> tempnodes = new ArrayList<>();
    public static ArrayList<String> PlayerID = new ArrayList<>();
    public static ArrayList <Color> AlreadyTakenColors = new ArrayList<>();
    public static ArrayList <String>  AlreadyTakenIDs = new ArrayList<>();
    private Color[] Colors = {Color.RED,Color.BLUE,Color.GREEN,Color.PURPLE};


    public BotManager(BotPlayer Bot){
        this.Bot = Bot;
    }

    public void SetBotID(){
        for(int i = 0; i < PlayerID.size();i++){
            for(int j = 0; j < AlreadyTakenIDs.size();j++){
                if(PlayerID.get(i) == AlreadyTakenIDs.get(j)){
                    ID_in_use = true;
                    break;
                }
                else {
                    ID_in_use = false;
                }
            }
            if(!ID_in_use){
                Bot.SetBotID(PlayerID.get(i));
                AlreadyTakenIDs.add(PlayerID.get(i));
                break;
            }
        }
    }
    public void SetBotColor(){ //RANG
        for(int i = 0; i < Colors.length ;i++){
            for(int j = 0;  j < AlreadyTakenColors.size();j++){
                if(Colors[i] == AlreadyTakenColors.get(j)){
                    Color_in_use = true;
                    break;
                }
                else {
                    Color_in_use = false;
                }
            }
            if(!Color_in_use){
                color = Colors[i];
                AlreadyTakenColors.add(color);
                break;
            }
        }
        if(color == Color.RED){

            Bot.SetBotColor(Color.DARKRED); //BOTColor = Color.DARKRED;
             Bot.SetAreaColor(color);        //AreaColor = color;
             Bot.SetTraceColor(Color.CORAL);  //TraceColor = Color.CORAL;
        }
        else if(color == Color.BLUE){

            Bot.SetBotColor(Color.DARKBLUE);
            Bot.SetAreaColor(color);
            Bot.SetTraceColor(Color.LIGHTBLUE);//BOTColor = Color.DARKBLUE;
                                           // AreaColor = color;
                                           // TraceColor = Color.LIGHTBLUE;
        }
        else if(color == Color.GREEN){
            Bot.SetBotColor(Color.DARKGREEN);
            Bot.SetAreaColor(color);
            Bot.SetTraceColor(Color.LIGHTGREEN);
                                            // BOTColor = Color.DARKGREEN;
                                            // AreaColor = color;
                                            // TraceColor = Color.LIGHTGREEN;
        }
        else if(color == Color.PURPLE){
            Bot.SetBotColor(Color.PURPLE);
            Bot.SetAreaColor(Color.MEDIUMPURPLE);
            Bot.SetTraceColor(Color.LAVENDER);

                                         //BOTColor = Color.PURPLE;
                                        // AreaColor = Color.MEDIUMPURPLE;
                                        // TraceColor = Color.LAVENDER;
        }

    }
    public void SetDefaultBotArea(){
        Random rand = new Random();
        while(true) {
            int RandNum = rand.nextInt(nodes.size() - 300);
            if(!nodes.get(RandNum).GetIs_passed()){
                for(int i =  nodes.get(RandNum).GetRow() ; i <= nodes.get(RandNum).GetRow() + 1; i++ ){
                    for(int j = nodes.get(RandNum).GetColumn(); j <= nodes.get(RandNum).GetColumn() + 1;j++){
                        for(int n = 0; n < nodes.size(); n++){
                            if(nodes.get(n).GetRow() == i  &&  nodes.get(n).GetColumn() == j){
                                tempnodes.add(nodes.get(n));
                            }
                        }
                    }
                }
                for(int j = 0 ; j < tempnodes.size();j++){
                    if(!tempnodes.get(j).GetIs_passed()){
                        is_empty = true;
                    }else{
                        is_empty = false;
                        break;
                    }
                }
            }
            if(is_empty){
                for(int i = 0; i < nodes.size();i++){
                    for(int j = 0; j < tempnodes.size();j++){
                        if(nodes.get(i) == tempnodes.get(j)){
                            nodes.get(i).setColor(Bot.GetAreaColor());
                            nodes.get(i).SetIs_colored(true);
                            Owner.put(nodes.get(i),Bot.GetBotID());
                        }
                    }
                }
                break;
            }

        }
    }
    public void AddPlayerID( int playernum){
        for(int i = 2; i <= playernum + 1; i++){
            PlayerID.add("PLAYER" + i);
        }
        System.out.println(PlayerID);
    }
}
