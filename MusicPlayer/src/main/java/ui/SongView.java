package ui;

import io.GraphicalFireRetriever;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class SongView extends Application {
    
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
    
        File file = GraphicalFireRetriever.getFile( "Audio files", "*.mp3", "*.wav", "*.ogg").get();
        Scene scene = new Scene(root, 300, 250);

        stage.setScene(scene);
        stage.show();
    }
}
