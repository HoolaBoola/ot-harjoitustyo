package ui;

import dao.PlaylistDao;
import dao.SongDao;
import database.DBManager;
import io.SongPlayer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

public class GraphicalUI extends Application implements UI {

    public void startApplication() {
        launch(GraphicalUI.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        DBManager man = new DBManager("player.db");
        SongDao songDao = new SongDao(man);
        SongPlayer player = new SongPlayer();
        
//        player.setSong(GraphicalFireRetriever.getFile("Audio file", "*.mp3").get());

        PlaylistDao playlistDao = new PlaylistDao(man);

        Scene scene = new Scene(new Pane());
        ScreenController screenController = new ScreenController(scene);
        
        URL url = getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        MenuController menu = loader.getController();
        menu.init(player, songDao, playlistDao);
        
        screenController.addScreen("menu", root);
        screenController.activate("menu");
        stage.setScene(scene);
        
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                player.quit();

            }
        });
        stage.show();
        
        
    }
}
