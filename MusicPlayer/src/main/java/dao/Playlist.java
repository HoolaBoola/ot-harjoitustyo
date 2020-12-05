package dao;

import java.sql.Blob;
import java.util.List;

public class Playlist {
    private int id;
    private Blob image;
    private List<Song> songs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Playlist(int id, Blob image, List<Song> songs) {
        this.id = id;
        this.image = image;
        this.songs = songs;
    }
}
