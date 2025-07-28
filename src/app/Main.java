package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import app.utility.AppUtility;
import app.utility.DatabaseUtility;
import app.View;
/**
 *
 * @author Arjun
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        AppUtility.setCurrentStage(stage);
        AppUtility.setCurrentUrl("/select-store");
        AppUtility.mapCurrentUrl();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        DatabaseUtility.closeConnection();
    }
    
}
