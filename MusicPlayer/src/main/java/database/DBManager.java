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
    private Connection connection;

    /**
     * Constructor with path and file name separate, calls another constructor.
     *
     * @param path     Path to the working directory the database should be created in / accessed.
     * @param fileName The filename that the database should be given.
     */
    public DBManager(String path, String fileName) {
        this(path + "/" + fileName);
    }

    /**
     * Constructor with only one parameter
     *
     * @param path Path + filename where the database should be.
     */
    public DBManager(String path) {

        // Does stuff so that SQLite JDBC driver is recognized?
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connect("jdbc:sqlite:" + path);
    }

    /**
     * Private method that creates a connection to a database and returns it. Shouldn't be called often.
     *
     * @param url Path to the correct location prefixed with the correct JDBC driver information
     * @return Optional containing either empty or java.sql.Connection
     */
    private Optional<Connection> connect(String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println(meta.getMaxColumnsInIndex());
                return Optional.of(conn);
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

}
