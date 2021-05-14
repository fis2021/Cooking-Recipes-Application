package org.tnh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        initDirectory();
        UserService.initDatabase();
        RecipeService.initDatabase();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("junior.fxml")));
        primaryStage.setTitle("Cooking-Recipes-Application");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath)) {
            boolean wasSuccessful;
            wasSuccessful = applicationHomePath.toFile().mkdirs();
            if (!wasSuccessful) {
                System.out.println("was not successful.");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
