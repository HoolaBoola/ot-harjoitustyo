package io;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SongPlayerTest {

    SongPlayer player;
    byte[] song;

    @Before
    public void setUp() throws Exception {
        player = new SongPlayer();
        song = FileUtils.readFileToByteArray(new File("testing.mp3"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getStatusReturnsNotPlayingFirst() {
        assertEquals("NOT PLAYING", player.getStatus());
    }

    @Test
    public void statusIsPlayingWhenStartingSongAndStoppedWhenQuit() {
        player.playSong(song);
        try {
            // Concurrency is a bitch
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("PLAYING", player.getStatus());

        player.quit();
        assertEquals("STOPPED", player.getStatus());
    }

    @Test
    public void statusIsPausedWhenPausedSong() {
        player.playSong(song);
        try {
            // Concurrency is a bitch
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.pauseSong();

        assertEquals("PAUSED", player.getStatus());

        player.quit();
        assertEquals("STOPPED", player.getStatus());

    }


    @Test
    public void continuePlaying() {
        player.playSong(song);
        try {
            // Concurrency is a bitch
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("PLAYING", player.getStatus());

        player.pauseSong();

        assertEquals("PAUSED", player.getStatus());

        player.continuePlaying();

        try {
            // Concurrency is a bitch
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("PLAYING", player.getStatus());

        player.quit();
    }

    @Test
    public void playSongOnTopOfAnotherDoesNotCrash() {
        player.playSong(song);
        
        try {
            // Concurrency is a bitch
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player.playSong(song);
        assertEquals("STOPPED", player.getStatus());
        try {
            // Concurrency is a bitch
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("PLAYING", player.getStatus());

        player.quit();

    }

    @Test
    public void pauseSongDoesNothingIfNoSong() {
        player.pauseSong();
        assertEquals("NOT PLAYING", player.getStatus());
    }

    @Test
    public void quitDoesNothingIfNoSong() {
        player.quit();
        assertEquals("NOT PLAYING", player.getStatus());

    }
}