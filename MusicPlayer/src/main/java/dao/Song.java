package dao;

import javafx.scene.image.Image;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Song {
    private int id;
    private String name;
    private Date created_at;
    private String artist;
    private File file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    private List<Playlist> playlists;

    public Song(int id, String name, Date created_at, String artist, File file) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.artist = artist;
        this.file = file;
        this.playlists = new ArrayList<>();
    }
}
