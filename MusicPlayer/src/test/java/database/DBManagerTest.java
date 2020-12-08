package database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DBManagerTest {
    
    DBManager db;
    String path = "testi.db";
    OutputStream baos;

    @Before
    public void setUp() {
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        
        System.setErr(ps);

        db = new DBManager(path);
    }
    
    @After
    public void tearDown() {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    public void classCreatesDB() {
        assertTrue(new File(path).exists());
        assertTrue(outputContainsString("Database created: true"));

    }
    
    @Test
    public void ifDBExistsItIsntCreated() {
        DBManager x = new DBManager(path);
        
        assertTrue(outputContainsString("Database created: false"));
    }
    
    @Test
    public void getConnection() {
        var result = db.getConnection();
        assertTrue(result.isPresent());
        
        var conn = result.get();

        try {
            var meta = conn.getMetaData();
            assertEquals("jdbc:sqlite:testi.db", meta.getURL());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testGetConnection() {
    }
    
    public boolean outputContainsString(String string) {
        return baos.toString().contains(string);
    }
}