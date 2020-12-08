package dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String name;
    private Date created_at;

    @Override
    public String toString() {
        return "Playlist{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", created_at=" + created_at +
            '}';
    }

    public Playlist(String name, Date created_at) {
        this.name = name;
        this.created_at = created_at;
    }

    public Playlist(int id, String name, Date created_at) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.songs = new ArrayList<>();
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

    private List<Song> songs = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}
