package dao;

import database.DBManager;

import java.sql.SQLException;
import java.util.List;

public class PlaylistDao implements Dao<Playlist, Integer> {
    
    private DBManager db;
    
    public PlaylistDao(DBManager db) {
        this.db = db;
    }
    
    @Override
    public void create(Playlist object) throws SQLException {

    }

    @Override
    public Playlist read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public Playlist update(Playlist object) throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Playlist> list() throws SQLException {
        return null;

    }
}