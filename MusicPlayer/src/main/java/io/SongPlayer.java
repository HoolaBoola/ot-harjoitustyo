package io;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Play songs using javazoom AdvancedPlayer
 */
public class SongPlayer {

    private AdvancedPlayer player;
    private String status = "NOT PLAYING";
    private InputStream stream;
    

    private int pausedOnFrame = 0;


    public String getStatus() {
        return status;
    }

    /**
     *  continue playing a song
     */
    public void continuePlaying() {

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
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }).start();

    }

    /**
     * set the player to play a song
     * @param file a byte array containing the song
     */
    public void playSong(byte[] file) {

        if (status.equals("PLAYING")) {
            quit();
        }
        pausedOnFrame = 0;
        stream = new ByteArrayInputStream(file);
        continuePlaying();

    }

    /**
     * pause the player
     */
    public void pauseSong() {
        if (player == null) {
            return;
        }

        player.stop();


    }

    /**
     * stop the player so that the concurrent thread is stopped
     */
    public void quit() {
        if (player == null) {
            return;
        }
        status = "STOPPED";
        player.close();
    }
}