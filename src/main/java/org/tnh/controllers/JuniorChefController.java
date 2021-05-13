package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class JuniorChefController {

    @FXML
    private TextField search;

    private static String searchValue;

    public static String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue () {
        searchValue = search.getText();
    }

    public void handleLogout(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cooking-Recipes-Application");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleSavedRecipes(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("saved_recipes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("List of saved recipes");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleShowRecipes(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("list_recipes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Junior Chef - List of recipes");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleSearch(ActionEvent event) throws Exception {
        try {
            RecipeService.uncompletedNameField(search.getText());
            setSearchValue();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("searchJuniorChef.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Junior Chef - Search");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch(UncompletedFieldsException e) {



        }
    }

}
