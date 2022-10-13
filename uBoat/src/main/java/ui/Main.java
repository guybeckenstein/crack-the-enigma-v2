package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Settings
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);
        primaryStage.setTitle("C.T.E Exercise 3");

        try {
            // Load master app and controller from FXML
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getClassLoader().getResource("ui/main/app.fxml");
            fxmlLoader.setLocation(url);
            ScrollPane root = fxmlLoader.load(Objects.requireNonNull(url).openStream());
            // Set scene
            Scene scene = new Scene(root, 902, 602);
            primaryStage.setScene(scene);
            primaryStage.show();
            // Handle quitting
            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (LoadException | NullPointerException e) {
            e.printStackTrace();
        }
}

    public static void main(String[] args) {
        Thread.currentThread().setName("ui/main");
        Application.launch(args);
    }
}