package dao;

import database.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Dao to handle Playlists between the application and the database
 */
public class PlaylistDao implements Dao<Playlist, Integer> {

    private DBManager db;

    public PlaylistDao(DBManager db) {
        this.db = db;
    }

    /**
     * @param object new Playlist to be created
     * @return true, unless an error interrupted the process. Then, returns false
     */
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

    /**
     * @param playlist
     * @return list of songs that are in the playlist
     */
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
        } catch (NullPointerException e) {
            // stmt throws a NPE if nothing is found?
        }
        return songs;
    }

    /**
     * Fetch playlist with the given key
     *
     * @param key the id of the playlist
     * @return
     */
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
        if (wanted != null) {
            wanted.setSongs(getSongsFromPlaylist(wanted));
        }
        return wanted;
    }

    /**
     * @param object
     * @return the updated object from the database
     */
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

    /**
     * Delete playlist from database and remove song-list pairs from SongPlaylist
     *
     * @param playlist
     * @return true, unless an error interrupted the process. Then, returns false
     */
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


    /**
     * Delete all entries from SongPlaylist where the playlist id is the same as the parameter's
     *
     * @param playlist
     * @return true, unless an error interrupted the process. Then, returns false
     */
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

    /**
     * @return list containing all playlists in the database
     */
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


    /**
     * "Converts" playlist into SQL statement
     *
     * @param stmt     prepared statement
     * @param playlist playlist from which the statement receives parameters
     */
    private static void sqlify(PreparedStatement stmt, Playlist playlist) {
        try {
            stmt.setString(1, playlist.getName());
            stmt.setDate(2, playlist.getCreated_at());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Extract a Playlist object from a resultset
     *
     * @param rs ResultSet containing the information necessary to form a playlist
     * @return the created playlist
     */
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