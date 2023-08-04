package com.example.paintioproject;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaPlayerManager {
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer SoundEffectPlayer;

    private static double playbackPosition;
    public static void loadMedia(String filePath) {
        Media media = new Media(new java.io.File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }
    public static void loadSoundEffect(String filePath){
        Media SoundEffect = new Media(new java.io.File(filePath).toURI().toString());
        SoundEffectPlayer = new MediaPlayer(SoundEffect);
    }

    public static void storePlaybackPosition() {
        playbackPosition = mediaPlayer.getCurrentTime().toSeconds();
    }

    public static void resumePlayback() {
        mediaPlayer.seek(new Duration(playbackPosition * 1000));
        mediaPlayer.play();
    }
    public static void StopPlaying(){
        mediaPlayer.stop();
    }
    public static void StopPlayingSoundEffect(){
        SoundEffectPlayer.stop();
    }
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
    public static MediaPlayer getSoundEffectPlayer(){
        return SoundEffectPlayer;
    }

}
