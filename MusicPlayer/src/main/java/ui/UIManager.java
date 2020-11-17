package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class UIManager extends Application {
    
    public static void start() {
        launch(UIManager.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        
        Scene scene = new Scene(new Pane());
        
        ScreenController screenController = new ScreenController(scene);
        
        screenController.addScreen("increment", FXMLLoader.load(getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml")));
        
        screenController.activate("increment");
        stage.setScene(scene);
        
        stage.show();
    }
}
