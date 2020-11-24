import database.DBManager;
import org.jline.builtins.Completers.*;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.PosixPtyTerminal;
import org.jline.terminal.impl.jna.linux.LinuxNativePty;
import ui.*;
import ui.UIManager;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {


        DBManager man = new DBManager("player.db");
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
