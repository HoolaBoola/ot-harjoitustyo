package dao;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SongTest {

    String artist, name;
    int id;
    Date created_at;
    byte[] bytes;
    String path;
    Song song;
    File file;


    @Before
    public void setUp() throws Exception {
        path = "testing.txt";
        id = 1;
        name = "testi";
        artist = "testaaja";
        created_at = Date.valueOf("2012-12-12");
        file = new File(path);
        writeToFile("hello", file);
        bytes = FileUtils.readFileToByteArray(file);
        song = new Song(id, name, created_at, artist, bytes);
    }

    boolean writeToFile(String contents, File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(contents);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @After
    public void tearDown() throws Exception {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void getId() {
        assertEquals(id, song.getId());
    }

    @Test
    public void setId() {
        int newId = 100;
        song.setId(newId);
        assertEquals(newId, song.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, song.getName());
    }

    @Test
    public void setName() {
        String newName = "test";
        song.setName(newName);
        assertEquals(newName, song.getName());
    }

    @Test
    public void getCreated_at() {
        assertEquals(Date.valueOf("2012-12-12"), song.getCreated_at());
    }

    @Test
    public void setCreated_at() {
        song.setCreated_at(new Date(System.currentTimeMillis()));
        assertEquals(new Date(System.currentTimeMillis()), song.getCreated_at());
    }

    @Test
    public void getArtist() {
        assertEquals(artist, song.getArtist());
    }

    @Test
    public void setArtist() {
        String newArtist = "tester";
        song.setArtist(newArtist);
        assertEquals(newArtist, song.getArtist());
    }

    @Test
    public void getFile() {
        assertEquals(bytes, song.getFile());
    }

    @Test
    public void setFile() {
        try {
            byte[] newFile = FileUtils.readFileToByteArray(new File("testing.mp3"));
            song.setFile(newFile);
            assertEquals(newFile, song.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getPlaylists() {
        var playlists = song.getPlaylists();
        
        assertEquals(0, playlists.size());
    }

    @Test
    public void setPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist("Testi", new Date(System.currentTimeMillis())));
        
        playlists.add(new Playlist("Testikakkonen", Date.valueOf("2020-12-01")));
        
        song.setPlaylists(playlists);
        
        assertEquals(2, song.getPlaylists().size());
        
    }

    @Test
    public void testToString() {
        assertEquals(artist + " - " + name, song.toString());
    }

    @Test
    public void info() {
        assertEquals(artist + " - " + name + " (Created: " + created_at + ")", song.info());
    }
    
    @Test
    public void constructorWithoutIdDoesntHaveOne() {
        song = new Song(name, created_at, artist, bytes);
        
        assertEquals(0, song.getId());
    }
}