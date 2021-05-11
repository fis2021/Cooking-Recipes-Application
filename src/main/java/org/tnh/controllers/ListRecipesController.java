package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ListRecipesController {

    public void handleBackAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
        if(stage.getTitle().equals("Head Chef - List of recipes"))
        {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("head.fxml")));
            stage.setTitle("Head Chef");
        }
        if(stage.getTitle().equals("Junior Chef - List of recipes"))
        {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("junior.fxml")));
            stage.setTitle("Junior Chef");
        }
        if(stage.getTitle().equals("List of recipes"))
        {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
            stage.setTitle("Cooking-Recipes-Application");
        }
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

}
