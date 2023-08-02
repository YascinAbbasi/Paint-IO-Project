package com.example.paintioproject;

import java.io.*;

public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    private String PlayerDataPath = "src/main/resources/Files/PlayerData";


    public String getPlayerDataPath() {
        return PlayerDataPath;
    }

    public void Write(Object obj, String Path){
        try {
            FileOutputStream fos = new FileOutputStream(Path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
