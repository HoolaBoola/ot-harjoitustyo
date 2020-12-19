package dao;

import database.DBManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDao implements Dao<Song, Integer> {

    private DBManager db;

    public SongDao(DBManager db) {
        this.db = db;
    }


    /**
     * Insert a new song into the database
     *
     * @param object a Song object
     * @return true, if creation was successful. Otherwise, returns false
     */
    @Override
    public boolean create(Song object) {
        String sql = "INSERT INTO Songs (name, created_at, artist, file) VALUES (?,?,?,?)";
        try {
            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();

            var stmt = conn.prepareStatement(sql);

            sqlify(stmt, object);

            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get a song from the database
     *
     * @param key the id of the wanted song
     * @return the song, if it exists. Otherwise, returns null
     */
    @Override
    public Song read(Integer key) {
        Song wanted = null;
        String sql = "SELECT * FROM Songs WHERE id == ?";
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

        return wanted;
    }


    /**
     * Update a song in the database
     *
     * @param object a Song object that has its id as a non-zero integer
     * @return the updated Song, if id is specified and exists. Otherwise, returns null
     */
    @Override
    public Song update(Song object) {
        if (object.getId() <= 0) {
            return null;
        }

        String sql = "UPDATE Songs SET name = ?, created_at = ?, artist = ?, file = ? WHERE id = ?";
        try {

            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            sqlify(stmt, object);
            stmt.setInt(5, object.getId());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return read(object.getId());
    }

    /**
     * Delete a song from the database
     *
     * @param song the id of the song
     * @return true, if the deletion was succesful. Otherwise, returns false (song doesn't exists, an error happened...)
     */
    @Override
    public boolean delete(Song song) {
        String sql = "DELETE FROM Songs WHERE id = ?";

        var result = db.getConnection();
        try {
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, song.getId());

            stmt.executeUpdate();

            conn.close();
            deleteSongFromAllPlaylists(song);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Get a list of all the songs in the database
     *
     * @return a List of Songs
     */
    @Override
    public List<Song> list() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM Songs";
        try {
            var result = db.getConnection();

            if (result.isEmpty()) {
                throw new SQLException();
            }
            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                songs.add(songify(rs));
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return songs;
    }

    public boolean addSongToPlaylist(Song song, Playlist playlist) {
        String sql = "INSERT INTO SongPlaylist (song_id, playlist_id) VALUES (?,?)";

        try {
            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();

            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, song.getId());
            stmt.setInt(2, playlist.getId());

            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteSongFromPlaylist(Song song, Playlist playlist) {
        String sql = "DELETE FROM SongPlaylist WHERE song_id = ? AND playlist_id = ?";

        try {
            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();

            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, song.getId());
            stmt.setInt(2, playlist.getId());

            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteSongFromAllPlaylists(Song song) {
        String sql = "DELETE FROM SongPlaylist WHERE song_id = ?";

        try {
            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();

            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, song.getId());
            
            stmt.executeUpdate();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Set a song's attributes to a PreparedStatement
     *
     * @param stmt PreparedStatement to be executed
     * @param song Song that is to be sqlified
     */
    public static void sqlify(PreparedStatement stmt, Song song) {
        try {
            stmt.setString(1, song.getName());
            stmt.setDate(2, song.getCreated_at());
            stmt.setString(3, song.getArtist());
            stmt.setBytes(4, song.getFile());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Extract a Song object from a ResultSet
     *
     * @param rs ResultSet containing values for each of Song's variables
     * @return the extracted Song, if succesful. Otherwise, null
     */
    public static Song songify(ResultSet rs) {
        Song song = null;
        try {

            song = new Song(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDate("created_at"),
                rs.getString("artist"),
                rs.getBytes("file")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return song;
    }

}
