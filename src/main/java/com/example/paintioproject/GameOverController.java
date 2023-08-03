package com.example.paintioproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class GameOverController implements Serializable {
    private static final long serialVersionUID = 1L;
    @FXML
    private Label ScoreLabel;
    @FXML
    public ImageView GameOverImageView;
    private Parent root;
    private Scene  scene;
    private Stage  stage;
    private File file = new File();
    private ArrayList <PlayerData> TempPlayers = new ArrayList<>();
    private int Score;
    private int PlayerNum = 10;
    private String PlayerID;
    private PlayerData playerdata;


    public void ContinueButton(ActionEvent e) throws IOException{
        ReadPlayer(file.getPlayerDataPath());
        FindPlayer(playerdata);
        TempPlayers.get(PlayerNum).Scores.add(Score);
        SetTopScore();
        file.Write(TempPlayers,file.getPlayerDataPath());
        Platform.exit();



       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        Image image = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai.jpg");
        LoginPageController loginPageController = loader.getController();
        loginPageController.LoginImageView.setImage(image);
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);*/
    }
    public void SetScoreLabel(int score){
        ScoreLabel.setText(Integer.toString(score));
        Score = score;
        System.out.println(Score +  " : SCOREEEEEEEEEEEEEE");
    }
    public void ReadPlayer(String Path){
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
        for(int i = 0; i < TempPlayers.get(PlayerNum).Scores.size();i++){
            if(TempPlayers.get(PlayerNum).GetTopScore() < TempPlayers.get(PlayerNum).Scores.get(i)){
                TempPlayers.get(PlayerNum).SetTopScore(TempPlayers.get(PlayerNum).Scores.get(i));
            }
        }
    }
    public void FindPlayer(PlayerData playerdata){
        for(int i = 0; i < TempPlayers.size();i++){
            if(TempPlayers.get(i).getUsername().equals(playerdata.getUsername())){
                PlayerNum = i;
                System.out.println(" PLAYER NUMMMMMMMM : " + PlayerNum);
                break;
            }
        }
    }
}
