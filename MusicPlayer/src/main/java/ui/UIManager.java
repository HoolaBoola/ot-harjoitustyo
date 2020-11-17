package ui;

import io.FileGraphicalRetriever;
import io.SongPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class UIManager extends Application {
    
    public static void start() {
        launch(UIManager.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        
        Scene scene = new Scene(new Pane());
        Optional<File> returned = FileGraphicalRetriever.getFile(stage, "Audio files", "*.mp3", "*.wav");
        if (returned.isPresent()) {
            SongPlayer player = new SongPlayer();
            player.playSong(returned.get());
        }
//        ScreenController screenController = new ScreenController(scene);
//        
//        // add a template to controller, "increment" is temporary name for the default view
//        screenController.addScreen("increment", FXMLLoader.load(getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml")));
//        
//        screenController.activate("increment");
//        stage.setScene(scene);
        stage.close();
//        stage.show();
    }
}
