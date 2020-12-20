package dao;

import database.DBManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Date;

import static org.junit.Assert.*;

public class PlaylistDaoTest {

    PlaylistDao dao;
    DBManager db;
    String path;
    Playlist playlist;

    @Before
    public void setUp() throws Exception {
        path = "test.db";
        db = new DBManager(path);
        dao = new PlaylistDao(db);
        playlist = new Playlist(1, "a", new Date(System.currentTimeMillis()));
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
        assertTrue(dao.create(playlist));
    }

    @Test
    public void read() {
        dao.create(playlist);

        Playlist s = dao.read(playlist.getId());

        assertEquals(playlist.getName(), s.getName());
        assertEquals(playlist.getCreated_at(), s.getCreated_at());
    }

    @Test
    public void update() {
        dao.create(playlist);

        playlist.setName("b");

        dao.update(playlist);

        Playlist s = dao.read(playlist.getId());

        assertEquals(playlist.getName(), s.getName());
        assertEquals(playlist.getCreated_at(), s.getCreated_at());
    }

    @Test
    public void updatingPlaylistWithoutIdDoesNothing() {
        Playlist p = new Playlist("a", new Date(System.currentTimeMillis()));
        dao.create(p);

        p.setName("asnalkdnalk");
        p = dao.update(p);
        assertNull(p);

        p = dao.read(1);

        assertEquals("a", p.getName());
    }

    @Test
    public void delete() {
        dao.create(playlist);

        dao.delete(playlist);

        Playlist p = dao.read(playlist.getId());

        assertNull(p);
    }

    @Test
    public void list() {
        dao.create(playlist);
        dao.create(playlist);
        dao.create(playlist);

        var playlists = dao.list();

        playlists.forEach(s -> assertEquals(playlist, s));
    }

    @Test
    public void sqlify() {
    }

    @Test
    public void songify() {
    }
}