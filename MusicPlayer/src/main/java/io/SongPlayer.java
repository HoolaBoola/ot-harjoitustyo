package io;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class SongPlayer {

    private AdvancedPlayer player;
    private String status = "NOT PLAYING";
    private InputStream stream;
    private ArrayDeque<byte[]> queue;
    private ArrayDeque<byte[]> previous = new ArrayDeque<>();

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
                    if (!status.equals("PAUSED")) {
                        status = "FINISHED";
                        if (queue != null && queue.size() > 0) {
                            byte[] next = queue.pollFirst();
                            playSong(next);
                        }
                    }
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
            return;
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
        status = "PLAYING";
        if (player != null) {
            player.close();
        }
        setSong(file);
        continuePlaying();
    }
    
    public void playQueue(List<byte[]> queue) {
        Collections.shuffle(queue);
        this.queue = new ArrayDeque<>(queue);
        playSong(this.queue.pollFirst());
    }
    
    public void setSong(byte[] bytes) {
        status = "SONG SET";
        pausedOnFrame = 0;
        stream = new ByteArrayInputStream(bytes);
        previous.addLast(bytes);
        if (previous.size() > 10) {
            previous.pollFirst();
        }
    }
    
    public void playNext() {
        if (queue == null || queue.size() == 0) {
            return;
        }
        playSong(queue.pollFirst());
    }
    
    public void playPrevious() {
        if (previous.size() <= 1) {
            return;
        }
        byte[] next = previous.pollLast();
        queue.addFirst(next);
        playSong(previous.pollLast());
    }

    /**
     * pause the player
     * @param status
     */
    public void pauseSong(String status) {
        if (player == null) {
            return;
        }
        this.status = status;
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