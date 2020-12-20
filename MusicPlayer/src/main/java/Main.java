import dao.PlaylistDao;
import dao.SongDao;
import database.DBManager;
import io.SongPlayer;
import ui.GraphicalUI;


public class Main {
    public static void main(String[] args) {

        var url = PropertyGetter.getDatabaseURL();
        url = url.replaceAll("\\$HOME", System.getProperty("user.home"));

        var man = new DBManager(url);
        var songDao = new SongDao(man);
        var playlistDao = new PlaylistDao(man);
        var player = new SongPlayer();

        GraphicalUI ui = new GraphicalUI(songDao, playlistDao, player);
        ui.startApplication();


    }
}
