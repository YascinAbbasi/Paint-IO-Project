package com.example.paintioproject;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Player extends GameThings  {
    //this class holds the player data and some method that is only used by the main player


    private Color PlayerColor;
    private Color TraceColor;
    private int PlayerScore = 0;
    private static boolean AlreadyColored = true;
    private MediaPlayer SoundEffectPlayer;
    private File file = new File();

    public void SetNodes(GridPane gp, int row_move, int column_move, Color PlayerColor, Color TraceColor){
        //this is the method that updates the map and checks the player's position to perform the game algorithm actions
        //How it works: Firstly, we remove all the elements from our GridPane and then set it up based on the player's movement.
        // Since the main player is constant and the map is moving, we check the player's position to perform the game algorithm actions.

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
                                                              //In each of these if conditions, we are seeking different possibilities to perform actions
                        if(row == 12 && column == 13){
                            if(GetAmIAKiller()){
                                                                       // Checking if we need to play the kill sound effect.

                                MediaPlayerManager.loadSoundEffect(file.DeathEffect);
                                SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                                SoundEffectPlayer.play();
                            }
                            ChecktoKill(nodes.get(j),"PLAYER1");   //check if we killed Someone
                            PlayerScore = CheckScore("PLAYER1");
                            System.out.println("Player Score : " + PlayerScore);
                        }
                        if(row == 12 && column == 13 &&(nodes.get(j).GetIs_colored() || !Objects.equals(Owner.get(nodes.get(j)), "PLAYER1"))){
                           nodes.get(j).SetPrevious(); //setting the previous data of the Playernode
                        }
                        if((row == 12 && column   == 13 )&& (!nodes.get(j).GetIs_colored() || !Objects.equals(Owner.get(nodes.get(j)), "PLAYER1"))){
                                //setting the new data of the Playernode
                            nodes.get(j).setColor(TraceColor);
                            nodes.get(j).SetIs_passed(true);
                            nodes.get(j).setOwnerID("PLAYER1");
                            AlreadyColored = false;
                        }
                        else if((row == 12 && column   == 13 )&& (nodes.get(j).GetIs_colored()) && (
                                Owner.get(nodes.get(j)) == "PLAYER1") && !AlreadyColored){
                            //Check if we have returned to our color to color the area

                                color_the_path(PlayerColor,"PLAYER1",TraceColor);
                                ColorArea(PlayerColor, "PLAYER1");
                                                                            //playing the coloring sound effect
                            MediaPlayerManager.loadSoundEffect(file.ColoringEffect);
                            SoundEffectPlayer = MediaPlayerManager.getSoundEffectPlayer();
                            SoundEffectPlayer.play();
                                AlreadyColored = true;

                        }
                    }
                }
            }
            row++;
        }
        gp.add(rect,12,12);

    }


    public void SetDefaultArea(Color color) { // setting up a default area for the Main Player
        PlayerScore = 0;
        for (int m = 11; m <= 13; m++) {
            for (int n = 11;n <=13;n++){
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).GetRow() == m && nodes.get(j).GetColumn() == n) {
                        nodes.get(j).setColor(color);
                        nodes.get(j).SetIs_colored(true);
                        nodes.get(j).SetPrevious();
                        Owner.put(nodes.get(j),"PLAYER1");
                        PlayerScore += 5;
                        break;
                    }
                }
            }
        }
    }

    public void SetPlayerColor(Color PlayerColor){  //setting the player Area & trace color
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
    public void SetPlayerScore(int PlayerScore){
        this.PlayerScore = PlayerScore;
    }

    public int GetPlayerScore() {
        return PlayerScore;
    }

}

