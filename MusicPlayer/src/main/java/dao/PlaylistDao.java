package dao;

import database.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistDao implements Dao<Playlist, Integer> {

    private DBManager db;

    public PlaylistDao(DBManager db) {
        this.db = db;
    }

    @Override
    public boolean create(Playlist object) {
        String sql = "INSERT INTO Playlists"
            + " (name, created_at)"
            + " VALUES (?, ?)";
        try {
            Optional<Connection> result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            Connection conn = result.get();
            PreparedStatement stmt = conn.prepareStatement(sql);
            sqlify(stmt, object);
            stmt.executeUpdate();
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public List<Song> getSongsFromPlaylist(Playlist playlist) {
        String sql = "SELECT Songs.* FROM Songs " +
            " JOIN SongPlaylist ON id = song_id " +
            " WHERE playlist_id = ?";
        
        List<Song> songs = new ArrayList<>();

        try {
            var result = db.getConnection();

            if (result.isEmpty()) {
                throw new SQLException();
            }
            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playlist.getId());
            var rs = stmt.executeQuery();

            while (rs.next()) {
                songs.add(SongDao.songify(rs));
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return songs;
    }

    @Override
    public Playlist read(Integer key) {
        Playlist wanted = null;
        String sql = "SELECT * FROM Playlists WHERE id == ?";
        try {
            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, key);
            var rs = stmt.executeQuery();
            if (rs.next()) {

                wanted = songify(rs);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        wanted.setSongs(getSongsFromPlaylist(wanted));

        return wanted;
    }

    @Override
    public Playlist update(Playlist object) {
        String sql = "UPDATE Playlists SET name = ?, created_at = ? WHERE id = ?";
        try {

            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            
            sqlify(stmt, object);
            stmt.setInt(3, object.getId());
            stmt.executeUpdate();
            
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return read(object.getId());    
    }

    @Override
    public boolean delete(Playlist playlist) {
        String sql = "DELETE FROM Playlists WHERE id = ?";

        var result = db.getConnection();
        try {
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playlist.getId());

            stmt.executeUpdate();

            conn.close();
            deleteAllSongsFromPlaylist(playlist);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;    
    }
    
    public boolean deleteAllSongsFromPlaylist(Playlist playlist) {
        String sql = "DELETE FROM SongPlaylist WHERE playlist_id = ?";

        var result = db.getConnection();
        try {
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playlist.getId());

            stmt.executeUpdate();

            conn.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Playlist> list() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM Playlists";
        try {
            var result = db.getConnection();

            if (result.isEmpty()) {
                throw new SQLException();
            }
            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                Playlist playlist = songify(rs);
                playlist.setSongs(getSongsFromPlaylist(playlist));
                playlists.add(playlist);
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return playlists;

    }

    private static void sqlify(PreparedStatement stmt, Playlist playlist) {
        try {
            stmt.setString(1, playlist.getName());
            stmt.setDate(2, playlist.getCreated_at());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Playlist songify(ResultSet rs) {
        Playlist playlist = null;
        try {

            playlist = new Playlist(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("created_at")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlist;
    }
}