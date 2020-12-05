package dao;

import database.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlaylistDao implements Dao<Playlist, Integer> {

    private DBManager db;

    public PlaylistDao(DBManager db) {
        this.db = db;
    }

    @Override
    public boolean create(Playlist object) throws SQLException {

        Optional<Connection> result = db.getConnection();
        if (result.isEmpty()) {
            return false;
        }

        Connection conn = result.get();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Playlists"
            + " (id, image)"
            + " VALUES (?, ?)");
        stmt.setInt(1, object.getId());
        stmt.setBlob(2, object.getImage());
        return true;
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
    public boolean delete(Integer key) throws SQLException {
        return false;
    }

    @Override
    public List<Playlist> list() throws SQLException {
        return null;

    }
}