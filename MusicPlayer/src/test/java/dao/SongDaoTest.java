package dao;

import database.DBManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Date;

import static org.junit.Assert.*;

public class SongDaoTest {

    SongDao dao;
    DBManager db;
    String path;
    Song song;

    @Before
    public void setUp() throws Exception {
        path = "test.db";
        db = new DBManager(path);
        dao = new SongDao(db);
        song = new Song(1, "a", new Date(System.currentTimeMillis()), "a", new byte[]{0, 1});
    }

    @After
    public void tearDown() throws Exception {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void create() {
        assertTrue(dao.create(song));
    }

    @Test
    public void read() {
        dao.create(song);

        Song s = dao.read(song.getId());

        assertEquals(song.getName(), s.getName());
        assertEquals(song.getCreated_at(), s.getCreated_at());
        assertEquals(song.getArtist(), s.getArtist());
        assertEquals(song.getFile().length, s.getFile().length);
    }

    @Test
    public void update() {
        dao.create(song);

        song.setName("b");

        dao.update(song);

        Song s = dao.read(song.getId());

        assertEquals(song.getName(), s.getName());
        assertEquals(song.getCreated_at(), s.getCreated_at());
        assertEquals(song.getArtist(), s.getArtist());
        assertEquals(song.getFile().length, s.getFile().length);
    }
    
    @Test
    public void updatingSongWithoutIdDoesNothing() {
        Song s = new Song("a", new Date(System.currentTimeMillis()), "a", new byte[]{0, 1});
        dao.create(s);
        
        s.setName("asnalkdnalk");
        s = dao.update(s);
        assertNull(s);
        
        s = dao.read(1);
        
        assertEquals("a", s.getName());
    }

    @Test
    public void delete() {
        dao.create(song);
        
        dao.delete(song.getId());
        
        Song s = dao.read(song.getId());
        
        assertNull(s);
    }

    @Test
    public void list() {
        dao.create(song);
        dao.create(song);
        dao.create(song);
        
        var songs = dao.list();
        
        songs.forEach(s -> assertEquals(song, s));
    }

    @Test
    public void sqlify() {
    }

    @Test
    public void songify() {
    }
}