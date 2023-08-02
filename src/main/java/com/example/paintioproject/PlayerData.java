package com.example.paintioproject;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private static final long serialVersionUID = 1L;
    private  String PlayerID;
    private int Score;
   private String username;
   private String password;

   public PlayerData (String username, String password){
       this.username = username;
       this.password = password;
   }


    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getScore() {
        return Score;
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
