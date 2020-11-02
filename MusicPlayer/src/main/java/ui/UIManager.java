package ui;

import database.DBManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIManager extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();

//        File file = SongFile.getFileFromExplorer(stage, "Audio files", "*.mp3", "*.wav", "*.ogg").get();
        DBManager manager = new DBManager(System.getProperty("user.dir"));
        manager.createDatabase("hei.sqlite");
        Scene scene = new Scene(root, 300, 250);

//        stage.setScene(scene);
        stage.show();
    }
}