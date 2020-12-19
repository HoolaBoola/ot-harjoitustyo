package ui;

import dao.Playlist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PlaylistListViewCell extends ListCell<Playlist> {
    private MenuController menu;
    
    public PlaylistListViewCell(MenuController menu, List<Playlist> playlists) {
        this.menu = menu;
    }
    
    private Playlist playlist;
    
    @FXML
    private TitledPane listPane;
    
    @FXML
    private AnchorPane anchor;
    
    @FXML
    private ListView playlistSongs;

    private FXMLLoader loader;

    @Override
    protected void updateItem(Playlist playlist, boolean empty) {
        super.updateItem(playlist, empty);

        if (empty || playlist == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        if (loader == null) {
            URL url = getClass().getClassLoader().getResource("FXML-templates/Playlist.fxml");
            loader = new FXMLLoader(url);
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setGraphic(anchor);

        listPane.setText(playlist.getName());
        this.playlist = playlist;
    }
}
