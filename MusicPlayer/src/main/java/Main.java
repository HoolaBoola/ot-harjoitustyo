import dao.PlaylistDao;
import dao.SongDao;
import database.DBManager;
import io.ConsoleIO;
import io.SongPlayer;
import ui.ConsoleUI;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        ConsoleIO io = new ConsoleIO(new Scanner(System.in));
        DBManager db = new DBManager("player.db");
        SongDao songs = new SongDao(db);
        PlaylistDao playlists = new PlaylistDao(db);
        SongPlayer player = new SongPlayer();


//        try {
//            player.playSong(songs.read(1).getFile());
//            io.nextLine();
//            player.pauseSong();
//        } catch (JavaLayerException e) {
//            e.printStackTrace();
//        }
        var ui = new ConsoleUI(io, playlists, songs, player);

        ui.startApplication();
        
        
//        GraphicalUI ui = new GraphicalUI();
//        ui.startApplication();
//        try {
//            UIManager manager;
//            manager = new UIManager(new ConsoleUI(TerminalBuilder.terminal()));
//            manager.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ;


    }
}
