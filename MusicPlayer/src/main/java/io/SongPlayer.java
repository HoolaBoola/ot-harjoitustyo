package io;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SongPlayer {

    private AdvancedPlayer player;
    private String status = "NOT PLAYING";
    private InputStream stream;

    public SongPlayer() {

    }

    private int pausedOnFrame = 0;


    public String getStatus() {
        return status;
    }

    public void continuePlaying() {
//        System.out.println(status);

        try {
            player = new AdvancedPlayer(stream);

            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent event) {
                    status = "PAUSED";
                    pausedOnFrame = event.getFrame();
                }

                @Override
                public void playbackStarted(PlaybackEvent evt) {
                    status = "PLAYING";
                    pausedOnFrame = 0;
                }
            });
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                player.play();
//                System.out.println(status);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void playSong(byte[] file) throws JavaLayerException {

        pausedOnFrame = 0;
        stream = new ByteArrayInputStream(file);
        continuePlaying();
    }

    public void pauseSong() {
        if (player == null) {
            return;
        }

        player.stop();


    }

    public void quit() {
        if (player == null) {
            return;
        }
        status = "STOPPED";
        player.close();
    }
}