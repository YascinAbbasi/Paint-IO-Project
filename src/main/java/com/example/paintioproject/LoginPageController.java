package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginPageController  implements Initializable, Serializable {
    private static final long serialVersionUID = 1L;
    private Parent GameModePage;
    private Parent SignUpPage;
    private Scene scene;
    private Stage stage;

    @FXML
  private Label WelcomeLabel;
    @FXML

private TextField username;
    @FXML
private TextField password;
    @FXML
    public TextArea LeaderBoardTextArea;
    @FXML
     public ImageView LoginImageView ;
    private File file = new File();
    private ArrayList <PlayerData> TempPlayers = new ArrayList<>();
    private String Username = "",Password = "";
    private static boolean AlreadyPlayed = false;
     private  MediaPlayer mediaplayer;



    public void LoginButton(ActionEvent e)throws IOException{
        ReadPlayer(file.getPlayerDataPath());
        Username = username.getText();
        Password = password.getText();
        if(Username.equals("") ||Password.equals("")){
            WelcomeLabel.setText("Fields Can't be empty!");
        }
        else{
            for(int i = 0; i < TempPlayers.size();i++){
                if(TempPlayers.get(i).getUsername().equals(Username) && TempPlayers.get(i).getPassword().equals(Password)){


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GameModePage.fxml"));
                    GameModePage = loader.load();
                    GameModeController gameModeController = loader.getController();
                    Image image2 = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai (2).jpg");
                    gameModeController.GameModeImageView.setImage(image2);
                    gameModeController.Setplayerdata(TempPlayers.get(i));
                    gameModeController.SetMediaPlayer(mediaplayer);
                    stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                    scene = new Scene(GameModePage);
                    stage.setScene(scene);
                    break;
                }else{
                    WelcomeLabel.setText("User Not Found!");
                }
            }
        }



        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("GameModePage.fxml"));
        GameModePage = loader.load();
        GameModeController gameModeController = loader.getController();
        Image image2 = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai (2).jpg");
        gameModeController.GameModeImageView.setImage(image2);
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(GameModePage);
        stage.setScene(scene);*/

    }
    public void SignUpButton(ActionEvent e1)throws IOException{
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("SignUpPage.fxml"));
        SignUpPage = loader2.load();
        SignUpPageController signUpPage =loader2.getController();
        Image image = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai (3).jpg");
        signUpPage.imageview.setImage(image);
        stage =  (Stage) ((Node) e1.getSource()).getScene().getWindow();
        scene = new Scene(SignUpPage);
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai.jpg");
        LoginImageView.setImage(image);
        ReadPlayer(file.getPlayerDataPath());
        String LeaderBoardData = "";
        for(int i = 0; i < TempPlayers.size();i++){
            LeaderBoardData += TempPlayers.get(i).getUsername() + "   " + TempPlayers.get(i).GetTopScore()+"\n";
        }
        LeaderBoardTextArea.setText(LeaderBoardData);
        Random Rand = new Random();
        int random = Rand.nextInt(2);
        if(!AlreadyPlayed) {
            switch (random) {
                case (0) -> {
                    MediaPlayerManager.loadMedia(file.Voyage);
                     mediaplayer = MediaPlayerManager.getMediaPlayer();
                    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
                    mediaplayer.play();
                    AlreadyPlayed = true;
                }
                case (1) -> {
                    MediaPlayerManager.loadMedia(file.NightShade);
                     mediaplayer = MediaPlayerManager.getMediaPlayer();
                    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
                    mediaplayer.play();
                    AlreadyPlayed = true;
                }
            }
        }
       // MediaPlayerManager.loadMedia(file.NightShade);
        //Media media = new Media(new java.io.File(file.Continue).toURI().toString());
        //  MediaPlayer mediaplayer = MediaPlayerManager.getMediaPlayer();
      //  mediaplayer.play();
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

}