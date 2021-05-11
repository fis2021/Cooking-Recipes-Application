package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tnh.exceptions.RecipeAlreadyExistsException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.services.RecipeService;

import java.io.IOException;
import java.util.Objects;



public class HeadChefController {

    @FXML
    public void handleCreateRecipeAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("create_recipe.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Create recipe");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void handleShowOwnRecipesAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("own_recipes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("List of own recipes");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void handleShowRecipesAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("list_recipes.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - List of recipes");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }

    public void handleLogoutAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cooking-Recipes-Application");
        stage.setScene(new Scene(root, 900, 550));
        stage.show();
    }
}
