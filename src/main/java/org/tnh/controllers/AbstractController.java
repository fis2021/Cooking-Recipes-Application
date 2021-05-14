package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tnh.model.Recipe;

import java.util.Objects;

public abstract class AbstractController {

    @FXML
    protected TableColumn<Recipe, String> recipeName, recipeCalories, recipeTime, recipeInstructions, recipeRating;

    protected void initVars(TableColumn<Recipe, String> recipeName, TableColumn<Recipe, String> recipeCalories, TableColumn<Recipe, String> recipeTime, TableColumn<Recipe, String> recipeInstructions, TableColumn<Recipe, String> recipeRating) {
        recipeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        recipeCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        recipeTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        recipeInstructions.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        recipeRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    }

    public void handleBackAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root;
        if(stage.getTitle().contains("Head Chef")) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("head.fxml")));
            stage.setTitle("Head Chef");
        } else if(stage.getTitle().contains("Junior Chef")) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("junior.fxml")));
            stage.setTitle("Junior Chef");
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
            stage.setTitle("Cooking-Recipes-Application");
        }
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

}
