package com.example.paintioproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SignUpPageController implements Serializable {
    private static final long serialVersionUID = 1L;
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label WelcomeLabel;
    @FXML
    public ImageView imageview;

    private File file = new File();
    private ArrayList <PlayerData> TempPlayers = new ArrayList<>();
    private PlayerData TempPlayer;

    private String Username = null;
    private String Password = null;




    public void SignUp(){
          ReadPlayer(file.getPlayerDataPath());
        boolean in_use = false;
        Username = username.getText();
        Password = password.getText();
        if(Username == null ||Password == null){
            WelcomeLabel.setText("Fields Shouldn't be empty!");
        }else {
            for(int i = 0; i < TempPlayers.size();i++){
                if(TempPlayers.get(i).getUsername() == Username){
                    WelcomeLabel.setText("Username is Already in Use!");
                    in_use = true;
                    break;
                }
            }
            if(!in_use) {
              //  TempPlayers.clear();
                TempPlayer = new PlayerData(Username, Password);
                TempPlayers.add(TempPlayer);
                file.Write(TempPlayers, file.getPlayerDataPath());
                WelcomeLabel.setText(" Welcome  " + Username + "  Please Return to LoginPage to Login!");
            }
        }

    }

    public void BackButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
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
