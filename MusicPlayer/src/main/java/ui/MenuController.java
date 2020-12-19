package ui;

import java.net.URL;
import java.util.*;

import dao.Playlist;
import dao.PlaylistDao;
import dao.Song;
import dao.SongDao;
import io.SongPlayer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class MenuController implements Initializable {

    private SongPlayer player;
    private Song currentSong;
    private SongDao songDao;
    private PlaylistDao playlistDao;
    private ObservableList<Song> songObservableList;

    public void init(SongPlayer player, SongDao songDao, PlaylistDao playlistDao) {
        this.songDao = songDao;
        this.playlistDao = playlistDao;
        this.player = player;
        playlists = playlistDao.list();
        songObservableList.addAll(songDao.list());

        System.out.println(songObservableList);
    }

    private List<Playlist> playlists;


    @FXML
    private Button playPause;

    @FXML
    private Label songPlaying;

    @FXML
    private ListView<Song> songList;

    @FXML
    private void newSong() {
        Dialog<Song> dialog = new Dialog<>();
        dialog.setTitle("New song");
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefSize(300, 400);
        dialog.getDialogPane().setMinHeight(300);
        dialog.getDialogPane().setMinWidth(400);


        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        byte[] file;

        Song song = new Song(null, null, null, null);

        TextField name = new TextField();
        name.setPromptText("name");
        TextField artist = new TextField();
        artist.setPromptText("artist");

        Button button = new Button("...");
        Label fileStatus = new Label("");
        BorderPane bp = new BorderPane();
        bp.setCenter(button);
        fileStatus.setPadding(new Insets(0, 40, 0, 0));
        bp.setRight(fileStatus);

        button.setOnAction(e -> {
            var result = GraphicalFireRetriever.getFile("Audio file", "*.mp3", "*.wav");
            if (result.isEmpty()) {
                fileStatus.setText("Error");
                return;
            }

            song.setFile(result.get());
            fileStatus.setText("OK");
        });

        gridPane.add(name, 1, 0);
        gridPane.add(new Label("Song name:"), 0, 0);
        gridPane.add(artist, 1, 1);
        gridPane.add(new Label("Artist:"), 0, 1);
        gridPane.add(bp, 1, 2);
        gridPane.add(new Label("File:"), 0, 2);


        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> name.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                song.setName(name.getText());
                song.setArtist(artist.getText());
                song.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
                return song;
            }
            return null;
        });

        Optional<Song> result = dialog.showAndWait();

        result.ifPresent(s -> {
            if (s.getFile() == null) {
                return;
            }
            songDao.create(s);
            songObservableList.add(s);
        });
    }


    @FXML
    private void songPlayPause() {

        switch (player.getStatus()) {
            case "PLAYING":
                player.pauseSong();
                playPause.setText("Play");
                break;
            case "NOT PLAYING":
                return;
            default:
                player.continuePlaying();
                playPause.setText("Pause");
                break;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        songObservableList = FXCollections.observableArrayList();

        songList.setItems(songObservableList);
        songList.setCellFactory(songListView -> new SongListViewCell(this));
    }

    public List<Playlist> playlists() {
        return playlists;
    }

    public void addSongToPlaylist(Song song, Playlist playlist) {
        System.out.println("toimii");
        songDao.addSongToPlaylist(song, playlist);
    }


    public void songPlay(Song song) {
        currentSong = song;
        songPlaying.setText(song.toString());
        player.playSong(song.getFile());
        playPause.setText("PAUSE");
    }

    public void deleteSong(Song song) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(300, 400);
        alert.getDialogPane().setMinHeight(300);
        alert.getDialogPane().setMinWidth(400);
        alert.setTitle("Delete song");
        alert.setHeaderText("You are about to delete song " + song.getName());
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            songDao.delete(song);
            songList.getItems().removeAll(song);
        }
    }

    public void editSong(Song song) {

        Dialog<Song> dialog = new Dialog<>();
        dialog.setTitle("Edit song");
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefSize(300, 400);
        dialog.getDialogPane().setMinHeight(300);
        dialog.getDialogPane().setMinWidth(400);


        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        byte[] file = song.getFile();

        TextField name = new TextField(song.getName());
        TextField artist = new TextField(song.getArtist());

        Button button = new Button("...");
        Label fileStatus = new Label("");
        BorderPane bp = new BorderPane();
        bp.setCenter(button);
        fileStatus.setPadding(new Insets(0, 40, 0, 0));
        bp.setRight(fileStatus);

        button.setOnAction(e -> {
            var result = GraphicalFireRetriever.getFile("Audio file", "*.mp3", "*.wav");
            if (result.isEmpty()) {
                fileStatus.setText("Error");
                return;
            }

            song.setFile(result.get());
            fileStatus.setText("OK");
        });

        gridPane.add(name, 1, 0);
        gridPane.add(new Label("Song name:"), 0, 0);
        gridPane.add(artist, 1, 1);
        gridPane.add(new Label("Artist:"), 0, 1);
        gridPane.add(bp, 1, 2);
        gridPane.add(new Label("File:"), 0, 2);

        songList.getSelectionModel().select(song);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> name.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                song.setName(name.getText());
                song.setArtist(artist.getText());
                return song;
            }
            return null;
        });

        Optional<Song> result = dialog.showAndWait();

        result.ifPresentOrElse(s -> {
            Song newSong = song;
            if (s.getFile() != null) {
                newSong = songDao.update(song);
            }
            songList.getSelectionModel().getSelectedItem().setArtist(newSong.getArtist());
            songList.getSelectionModel().getSelectedItem().setName(newSong.getName());
            songList.getSelectionModel().getSelectedItem().setFile(newSong.getFile());
            songList.refresh();
//            songList.getItems().addAll(newSong);

        }, () -> songList.getItems().addAll(song));
    }
}
