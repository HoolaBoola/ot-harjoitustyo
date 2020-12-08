package ui;

//import dao.*;
//import database.DBManager;
//import javafx.application.Application;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.Date;
//
//public class GraphicalUI extends Application implements UI {
//
//    public void startApplication() {
//        launch(GraphicalUI.class);
//    }
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        DBManager man = new DBManager("player.db");
////        SongDao dao = new SongDao(man);
////        var song = new Song(
////            1,
////            "testi",
////            new Date(System.currentTimeMillis()),
////            "moimoi",
////            GraphicalFireRetriever.getFile("audio files", "*.mp3", "*.wav").get()
////        );
////
////        System.out.println(dao.create(song));
////        
////        song.setName("moi");
////        
////        System.out.println(dao.update(song));
////        System.out.println(dao.delete(1));
////        System.out.println(dao.list());
//
//        PlaylistDao dao = new PlaylistDao(man);
//        var playlist = new Playlist(1, "toka", new Date(System.currentTimeMillis()));
//
//        System.out.println(dao.create(playlist));
//        System.out.println(dao.create(playlist));
//        System.out.println(dao.list());
//
////        Scene scene = new Scene(new Pane());
////        Optional<File> returned = GraphicalFireRetriever.getFile(stage, "Audio files", "*.mp3", "*.wav");
////        if (returned.isPresent()) {
////            SongPlayer player = new SongPlayer();
////            player.playSong(returned.get());
////        }
////        ScreenController screenController = new ScreenController(scene);
//
//        // add a template to controller, "increment" is temporary name for the default view
////        screenController.addScreen("increment", FXMLLoader.load(getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml")));
//
////        screenController.activate("increment");
////        stage.setScene(scene);
//        stage.close();
//        System.exit(0);
////        stage.show();
//    }
//}
