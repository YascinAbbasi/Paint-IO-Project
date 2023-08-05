package com.example.paintioproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;

public class GameOverController implements Serializable {
                                //In this class, we display the player's score, and this class also serves as the game's closing screen
    private static final long serialVersionUID = 1L;
    @FXML
    private Label ScoreLabel;
    @FXML
    public ImageView GameOverImageView;
    private File file = new File();
    private ArrayList <PlayerData> TempPlayers = new ArrayList<>();
    private int Score;
    private int PlayerNum = 10;
    private PlayerData playerdata;


    public void ContinueButton(){        //saving the player score and comparing it with the user's top score
        ReadPlayer(file.getPlayerDataPath());
        FindPlayer(playerdata);
        TempPlayers.get(PlayerNum).Scores.add(Score);
        SetTopScore();
        file.Write(TempPlayers,file.getPlayerDataPath());
        Platform.exit();
    }
    public void SetScoreLabel(int score){
        ScoreLabel.setText(Integer.toString(score));
        Score = score;
        System.out.println(Score +  " : SCOREEEEEEEEEEEEEE");
    }
    public void ReadPlayer(String Path){     //reading the users from the file to be able to update them
        try {
            FileInputStream fis = new FileInputStream(Path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            TempPlayers = (ArrayList<PlayerData>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void Setplayerdata(PlayerData playerdata ){
        this.playerdata = playerdata;
        System.out.println(playerdata.getUsername());
    }
    public void SetTopScore(){
        //comparing the corrent score with the top score
        for(int i = 0; i < TempPlayers.get(PlayerNum).Scores.size();i++){
            if(TempPlayers.get(PlayerNum).GetTopScore() < TempPlayers.get(PlayerNum).Scores.get(i)){
                TempPlayers.get(PlayerNum).SetTopScore(TempPlayers.get(PlayerNum).Scores.get(i));
            }
        }
    }
    public void FindPlayer(PlayerData playerdata){ //finding the player from the readed users
        for(int i = 0; i < TempPlayers.size();i++){
            if(TempPlayers.get(i).getUsername().equals(playerdata.getUsername())){
                PlayerNum = i;
                System.out.println(" PLAYER NUMMMMMMMM : " + PlayerNum);
                break;
            }
        }
    }
}
