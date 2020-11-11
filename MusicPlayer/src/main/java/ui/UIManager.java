package ui;

import database.DBManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UIManager extends Application {
    
    public static void start() {
        launch(UIManager.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML-templates/IncrementUI.fxml"));

        stage.setScene(new Scene(root));
        
        stage.show();
    }
}
