package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private String path;

    public DBManager(String path) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.path = path;
    }

    public boolean createDatabase(String fileName) {
        String url = "jdbc:sqlite:" + path + "/" + fileName;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:testi.db")) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println(meta);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean connectDatabase() {
        return false;
    }
}
