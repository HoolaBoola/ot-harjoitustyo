package dao;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class FileRetriever {

    /**
     * Open the system default file explorer and let user select a file of a type specified with <i>filters</i>
     * 
     * @param stage
     * @param typeName The type explanation shown in file explorer (e.g. "Audio files")
     * @param filters The file types wanted (e.g. "*.mp3", "*.wav")
     * @return Optional containing either the selected file, or empty, if no file was chosen or something else went wrong.
     */
    public static Optional<File> getFileFromExplorer(Stage stage, String typeName, String... filters) {
        
        
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
            new FileChooser.ExtensionFilter(
                typeName + " (" + String.join(", ", filters) + ")", filters);

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
