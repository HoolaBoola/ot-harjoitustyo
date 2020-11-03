package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Handles connections between application and database - no other method should have anything to do with databases.
 */
public class DBManager {

    private String path;
    /**
     *
     * @param path Where the database should be.
     */
    public DBManager(String path) {
        this.path = path;
        
        // Does stuff so that SQLite JDBC driver is recognized?
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connect("jdbc:sqlite:" + path);
    }

    /**
     * Private method that creates a connection to a database and returns it.
     *
     * @param url Path to the correct location prefixed with the correct JDBC driver information
     * @return Optional containing either empty or java.sql.Connection
     */
    private Optional<Connection> connect(String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println(meta.getURL());
                return Optional.of(conn);
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

}
