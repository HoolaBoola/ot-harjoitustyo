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

    @Override
    public Song update(Song object) {
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

    @Override
    public boolean delete(Integer key) {
        String sql = "DELETE FROM Songs WHERE id = ?";

        var result = db.getConnection();
        try {
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, key);

            stmt.executeUpdate();

            conn.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

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
