package ui;

import dao.Song;
import dao.SongDao;
import dao.UI;
import database.DBManager;
import io.GraphicalFireRetriever;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class GraphicalUI extends Application implements UI {

    public void startApplication() {
        launch(GraphicalUI.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        DBManager man = new DBManager("player.db");
        SongDao dao = new SongDao(man);
        var song = new Song(
            1,
            "moi",
            new Date(System.currentTimeMillis()),
            "jaa",
            GraphicalFireRetriever.getFile("audio files", "*.mp3", "*.wav").get()
        );

        try {
            System.out.println(dao.create(song));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        Scene scene = new Scene(new Pane());
//        Optional<File> returned = GraphicalFireRetriever.getFile(stage, "Audio files", "*.mp3", "*.wav");
//        if (returned.isPresent()) {
//            SongPlayer player = new SongPlayer();
//            player.playSong(returned.get());
//        }
//        ScreenController screenController = new ScreenController(scene);

        // add a template to controller, "increment" is temporary name for the default view
//        screenController.addScreen("increment", FXMLLoader.load(getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml")));

//        screenController.activate("increment");
//        stage.setScene(scene);
        stage.close();
//        stage.show();
    }
}
