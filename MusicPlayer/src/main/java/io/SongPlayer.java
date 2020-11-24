package io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

import java.io.File;

public class SongPlayer {
    
    private Player player;
    
    public void close() {
        if (player != null) player.close();
    }

    // play the MP3 file to the sound card
    public void playSong(File file) {
        String filename = file.toString();
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        } catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try {
                    player.play();
                    closePlayer();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }.start();
    }
    
    public void closePlayer() {
        player.close();
    }
    
    public boolean isComplete(){
        return player.isComplete();
    }
    
    public int getPosition() {
        return player.getPosition();
    }
    
}