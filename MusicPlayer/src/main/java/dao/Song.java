package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Song {
    private int id;
    private String name;
    private Date created_at;
    private String artist;
    private byte[] file;

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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        return artist + " - " + name;
    }

    public String info() {
        return artist + " - " + name + " (Created: " + created_at + ")";
    }

    private List<Playlist> playlists = new ArrayList<>();

    public Song(String name, Date created_at, String artist, byte[] file) {
        this.name = name;
        this.created_at = created_at;
        this.artist = artist;
        this.file = file;
    }

    public Song(int id, String name, Date created_at, String artist, byte[] file) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.artist = artist;
        this.file = file;
        this.playlists = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getName().equals(song.getName()) &&
            getCreated_at().equals(song.getCreated_at()) &&
            getArtist().equals(song.getArtist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCreated_at(), getArtist());
    }
}
