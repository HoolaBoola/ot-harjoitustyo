package io;

import dao.FileRetriever;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Optional;

public class GraphicalFireRetriever implements FileRetriever {

    /**
     * Open the system default file explorer and let user select a file of a type specified with <i>filters</i>
     *
     * @param typeName The type explanation shown in file explorer (e.g. "Audio files")
     * @param filters The file types wanted (e.g. "*.mp3", "*.wav") in regex form
     * @return Optional containing either the selected file, or empty, if no file was chosen or something else went wrong.
     */
    
    // refactor method so that Stage isn't required...
    public static Optional<byte[]> getFile(String typeName, String... filters) {
        Stage stage = new Stage();
        
        
        FileChooser fileChooser = new FileChooser();
        
        // add filters to file chooser, files of other types aren't shown
        FileChooser.ExtensionFilter fileExtensions =
            new FileChooser.ExtensionFilter(
                typeName + " (" +
                    String.join(", ", filters) // Turns array of filters (for example ["*.mp3", "*.gif"]) into string "*.mp3, *.gif"
                    + ")", 
                filters);

        fileChooser.getExtensionFilters().add(fileExtensions);
        
        try{
            File file = fileChooser.showOpenDialog(stage);
            System.out.println("File chosen: " + file.getAbsolutePath());
            return Optional.of(FileUtils.readFileToByteArray(file));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<File> getFile() {
        return Optional.empty();
    }
}
