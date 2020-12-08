package dao;

import org.apache.commons.io.FileUtils;
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

public class PlaylistTest {

    String name;
    int id;
    Date created_at;
    Playlist list;


    @Before
    public void setUp() throws Exception {
        id = 1;
        name = "testi";
        created_at = Date.valueOf("2012-12-12");
        list = new Playlist(id, name, created_at);
    }

    @Test
    public void testToString() {
        assertEquals("Playlist{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", created_at=" + created_at +
            '}', list.toString());
    }

    @Test
    public void getName() {
        assertEquals(name, list.getName());
    }

    @Test
    public void setName() {
        String newName = "test";
        list.setName(newName);
        
        assertEquals(newName, list.getName());
    }

    @Test
    public void getCreated_at() {
        assertEquals(created_at, list.getCreated_at());
    }

    @Test
    public void setCreated_at() {
        Date newDate = new Date(System.currentTimeMillis());
        list.setCreated_at(newDate);
        
        assertEquals(newDate, list.getCreated_at());
    }

    @Test
    public void getId() {
        assertEquals(id, list.getId());
    }

    @Test
    public void setId() {
        int newId = 2;
        list.setId(newId);
        
        assertEquals(newId, list.getId());
    }

    @Test
    public void getSongs() {
        var songs = list.getSongs();
        
        assertEquals(0, songs.size());
        
    }

    @Test
    public void setSongs() {
        List<Song> newSongs = new ArrayList<>();
        newSongs.add(new Song("a", created_at, "a", new byte[] {0, 1}));
        newSongs.add(new Song("b", new Date(System.currentTimeMillis()), "b", new byte[] {12, 1}));

        list.setSongs(newSongs);
        
        assertEquals(2, list.getSongs().size());
    }
}