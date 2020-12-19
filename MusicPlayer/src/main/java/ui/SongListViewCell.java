package ui;

import dao.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class SongListViewCell extends ListCell<Song> {

    @FXML
    private Label songName;
    
    @FXML
    private Label songArtist;
    
    @FXML
    private AnchorPane anchor;

    private FXMLLoader loader;
    

    
    @Override
    protected void updateItem(Song song, boolean empty) {
        super.updateItem(song, empty);

        if (empty || song == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        if (loader == null) {
            URL url = getClass().getClassLoader().getResource("FXML-templates/Song.fxml");
            loader = new FXMLLoader(url); 
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setGraphic(anchor);

        songName.setText(song.getName());
        songArtist.setText(song.getArtist());
    }
}
