package io;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.ByteArrayInputStream;

public class SongPlayer {

    AdvancedPlayer player;

    public SongPlayer() {

    }

    private int pausedOnFrame = 0;
    
    public void playSong(byte[] file) throws JavaLayerException {

        pausedOnFrame = 0;
        player = new AdvancedPlayer(new ByteArrayInputStream(file));
        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackFinished(PlaybackEvent event) {
                pausedOnFrame = event.getFrame();
            }
        });
        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void pauseSong() {
        System.out.println(player);
        if (player == null) {
            return;
        }

        player.stop();
    }
}