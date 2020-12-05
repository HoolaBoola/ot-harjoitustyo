import dao.Song;
import dao.SongDao;
import database.DBManager;
import io.GraphicalFireRetriever;
import org.jline.builtins.Completers.*;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.PosixPtyTerminal;
import org.jline.terminal.impl.jna.linux.LinuxNativePty;
import ui.*;
import ui.UIManager;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {


        GraphicalUI ui = new GraphicalUI();
        ui.startApplication();
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
