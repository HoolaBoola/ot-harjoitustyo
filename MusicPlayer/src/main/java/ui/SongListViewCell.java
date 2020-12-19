package ui;

import dao.Playlist;
import dao.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class SongListViewCell extends ListCell<Song> {

    private MenuController menu;

    public SongListViewCell(MenuController menu) {
        this.menu = menu;
    }

    @FXML
    private Label songName;

    @FXML
    private Label songArtist;

    @FXML
    private Button playSong;

    @FXML
    private AnchorPane anchor;

    @FXML
    private MenuButton addToPlaylistMenuButton;
    
    
    public void addSongToPlaylist(Playlist playlist) {
        menu.addSongToPlaylist(song, playlist);
    }

    private FXMLLoader loader;

    @FXML
    public void play() {
        System.out.println("Mmm");
        menu.songPlay(song);
    }

    @FXML
    public void delete() {
        menu.deleteSong(song);
    }
    
    @FXML
    public void edit() {
        menu.editSong(song);
    }

    private Song song;

    public Song getSong() {
        return song;
    }

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
        this.song = song;

        List<MenuItem> items = menu.playlists().stream().map(p -> {
            var i = new MenuItem(p.getName());
            i.setOnAction(e -> addSongToPlaylist(p));
            return i;
        }).collect(Collectors.toList());
        addToPlaylistMenuButton.getItems().addAll(items);
    }
}
