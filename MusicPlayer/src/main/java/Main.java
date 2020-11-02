import database.DBManager;
import ui.UIManager;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {

        DBManager man = new DBManager("/home/hajajaim/koulu/ohte/harjoitustyo/MusicPlayer/", "moi");
//        launch(UIManager.class);
    }
}
