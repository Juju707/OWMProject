package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that starts the GUI window
 * @author Juju
 * @version 1.0
 */
public class Main extends Application {
    /**
     * Sets the primary stage,sets the width and height,sets the title and shows the window
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Weather Station");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    /**
     * Main that launches arguments
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
