package dao;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SongFile {

    public static Optional<File> openExplorer(Stage stage) {
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
            new FileChooser.ExtensionFilter(
                "Audio files", "*.mp3", "*.wav", "*.ogg");

        fileChooser.getExtensionFilters().add(fileExtensions);
        
        try{
            File file = fileChooser.showOpenDialog(stage);
            System.out.println("File chosen: " + file.getAbsolutePath());
            return Optional.of(file);
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}
