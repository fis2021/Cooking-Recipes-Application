package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ListRecipesController {

    public void handleBackFirstPage(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cooking-Recipes-Application");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

}
