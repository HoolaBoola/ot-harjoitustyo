package ui;

import dao.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicalUI extends Application implements UI {
    
    public void startApplication() {
        launch(GraphicalUI.class);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(new Pane());
//        Optional<File> returned = GraphicalFireRetriever.getFile(stage, "Audio files", "*.mp3", "*.wav");
//        if (returned.isPresent()) {
//            SongPlayer player = new SongPlayer();
//            player.playSong(returned.get());
//        }
        ScreenController screenController = new ScreenController(scene);

        // add a template to controller, "increment" is temporary name for the default view
        screenController.addScreen("increment", FXMLLoader.load(getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml")));

        screenController.activate("increment");
        stage.setScene(scene);
        stage.close();
        stage.show();
    }
}
