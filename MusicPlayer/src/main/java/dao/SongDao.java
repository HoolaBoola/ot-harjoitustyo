package dao;

import database.DBManager;

import java.sql.SQLException;
import java.util.List;

public class SongDao implements Dao<Song, Integer> {

    private DBManager db;

    public SongDao(DBManager db) {
        this.db = db;
    }


    @Override
    public void create(Song object) throws SQLException {
        
    }

    @Override
    public Song read(Integer key) throws SQLException {
        return null;
    }

    @Override
    public Song update(Song object) throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Song> list() throws SQLException {
        return null;
    }
}
