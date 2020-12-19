package ui;

import dao.Song;
import dao.SongDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewSongView implements Initializable {
    
    @FXML
    private String songName;
    
    @FXML
    private String songArtist;
    
    @FXML
    private Label fileStatus;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button okButton;
    
    private byte[] file;
    
    private SongDao dao;
    
    private FXMLLoader loader;
    
    @FXML
    public void getSongFile() {
        var result = GraphicalFireRetriever.getFile("Audio file", "*.mp3");

        if (result.isEmpty()) {
            fileStatus.setText("File not found!");
            return;
        }
        
        file = result.get();
        fileStatus.setText("✅");
    }
    
    @FXML
    public void okClicked() {
        dao.create(new Song(songName, new java.sql.Date(System.currentTimeMillis()), songArtist, file));
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void cancelClicked() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    public void init(SongDao dao) {
        this.dao = dao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL url = getClass().getClassLoader().getResource("FXML-templates/NewSong.fxml");
        loader = new FXMLLoader(url);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
