import database.DBManager;
import ui.UIManager;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {

        DBManager man = new DBManager(System.getProperty("user.dir"), "moi");
//        launch(UIManager.class);
    }
}
