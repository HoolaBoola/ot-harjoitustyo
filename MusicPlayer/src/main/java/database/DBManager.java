package database;

import java.io.*;
import java.sql.*;
import java.util.Optional;


public class DBManager {

    private String path;

    /**
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
//        connect("jdbc:sqlite:" + path);
        boolean dbCreated = createDB();
        System.err.println("Database created: " + dbCreated);
    }

    /**
     * calls getConnection(url)
     *
     * @return Optional containing a Connection, or empty if an error occurred
     */
    public Optional<Connection> getConnection() {
        return getConnection("jdbc:sqlite:" + path);
    }

    /**
     * method that creates a connection to a database and returns it.
     *
     * @param url Path to the correct location prefixed with the correct JDBC driver information
     * @return Optional containing either empty or java.sql.Connection
     */
    public Optional<Connection> getConnection(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            if (null == conn) {
                return Optional.empty();
            }

            DatabaseMetaData meta = conn.getMetaData();
//            System.out.println(meta.getURL());
//            System.out.println(conn.isClosed());

            return Optional.of(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    private boolean createDB() {
        File f = new File(path);
        if (f.isFile()) {
            // do something
            return false;
        }
        Optional<Connection> result = getConnection();
        if (result.isEmpty()) {
            return false;
        }

        Connection conn = result.get();
        insertStuffs(conn);

        return true;
    }

    private void insertStuffs(Connection conn) {


        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/database.sql")));
            StringBuffer sb = new StringBuffer();
            while (br.ready()) {
                sb.append(br.readLine());
            }
            br.close();

            String[] inst = sb.toString().split(";");

            Statement st = conn.createStatement();

            for (int i = 0; i < inst.length; i++) {

                if (!inst[i].isEmpty()) {
                    try {
                        st.executeUpdate(inst[i]);
                    } catch (SQLException e) {
                        // table already exists
                    }
//                    System.out.println(" >>" + inst[i]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
