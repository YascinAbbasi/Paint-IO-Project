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
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
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


    public void LoginButton(ActionEvent e)throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameModePage.fxml"));
        GameModePage = loader.load();
        GameModeController gameModeController = loader.getController();
        Image image2 = new Image("file:src/main/resources/images/abstract-multi-colored-wave-pattern-shiny-flowing-modern-generated-by-ai (2).jpg");
        gameModeController.GameModeImageView.setImage(image2);
        stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(GameModePage);
        stage.setScene(scene);

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
    }
}