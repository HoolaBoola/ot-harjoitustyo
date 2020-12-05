package dao;

import database.DBManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public boolean create(Song object) throws SQLException {
        String sql = "INSERT INTO Songs (name, created_at, artist, file) VALUES (?,?,?,?)";
        try {
            var result = db.getConnection();
            if (result.isEmpty()) {
                throw new SQLException();
            }

            var conn = result.get();

            var stmt = conn.prepareStatement(sql);
            stmt.setString(1, object.getName());
            stmt.setDate(2, object.getCreated_at());
            stmt.setString(3, object.getArtist());
            stmt.setBinaryStream(4, new FileInputStream(object.getFile()), (int) object.getFile().length());
            stmt.executeUpdate();
            conn.close();
            
            return true;
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Song read(Integer key) {
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

                return songify(rs);
            }

        } catch (SQLException e) {

        }

        return null;
    }

    @Override
    public Song update(Song object) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Integer key) throws SQLException {
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return songs;
    }

    private static Song songify(ResultSet rs) throws SQLException {
        Song song = null;
        song = new Song(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getDate("created_at"),
            rs.getString("artist"),
            new File(String.valueOf(rs.getBlob("file"))
            )
        );


        return song;
    }
}
