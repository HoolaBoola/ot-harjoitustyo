package ui;

import java.net.URL;
import java.util.*;

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
import javafx.util.Pair;

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

        songObservableList.addAll(songDao.list());

        System.out.println(songObservableList);


//        ObservableList<SongController> oList = FXCollections.observableArrayList(list);
//        songList.setItems(oList);
//        List<SongController> songControllers = songs.stream().map(song -> new SongController(song)).collect(Collectors.toList());
    }

    @FXML
    private Button playPause;

    @FXML
    private ListView<Song> songList;

    @FXML
    private void newSong() {
        Dialog<Song> dialog = new Dialog<>();
        dialog.setTitle("New song");

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

        System.out.println(songList.getItems());
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
        songList.setCellFactory(songListView -> new SongListViewCell());
    }


}
