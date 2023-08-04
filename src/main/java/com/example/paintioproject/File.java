package com.example.paintioproject;

import java.io.*;

public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    private String PlayerDataPath = "src/main/resources/Files/PlayerData";

    public String NightShade = "src/main/resources/AudioFiles/AdhesiveWombat-NightShadeNOCOPYRIGHT 8-bitMusic.mp3";
    public String Voyage = "src/main/resources/AudioFiles/Voyage(NoCopyrightMusic).mp3";
    public String Continue = "src/main/resources/AudioFiles/Continue(ArcadeNoCopyrightMusic).mp3";
    public String UpperCut = "src/main/resources/AudioFiles/Uppercut(ArcadeNoCopyrightMusic).mp3";
    public String Shoryuken = "src/main/resources/AudioFiles/Shoryuken(ArcadeNoCopyrightMusic).mp3";
    public String BelletAEffect = "src/main/resources/AudioFiles/BulletAsoundEffect.mp3";
    public String BelletBEffect = "src/main/resources/AudioFiles/BulletBSoundEffect.mp3";
    public String ColoringEffect = "src/main/resources/AudioFiles/ColorSoundEffect.mp3";
    public String DeathEffect = "src/main/resources/AudioFiles/DeathSoundEffect.mp3";


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
