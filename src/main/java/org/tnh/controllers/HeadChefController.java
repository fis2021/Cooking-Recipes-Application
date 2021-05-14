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
import org.tnh.exceptions.CouldNotFindRecipeException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.services.RecipeService;

import java.io.IOException;
import java.util.Objects;

public class HeadChefController {

    private Stage stage;
    private Parent root;

    @FXML
    private Text searchMessage;
    @FXML
    private TextField search;

    private static String searchValue;

    public static String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue () {
        searchValue = search.getText();
    }

    @FXML
    public void handleCreateRecipeAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("create_recipe.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - Create recipe");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleShowOwnRecipesAction(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("own_recipes.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - List of owned recipes");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleShowRecipesAction(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("list_recipes.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - List of recipes");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleLogoutAction(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cooking-Recipes-Application");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleSearch(ActionEvent event) throws Exception {
        try {
            RecipeService.uncompletedNameField(search.getText());
            RecipeService.couldNotFindRecipe(search.getText());
            setSearchValue();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("searchHeadChef.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Head Chef - Search");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (UncompletedFieldsException | CouldNotFindRecipeException e) {
            searchMessage.setText(e.getMessage());
        }
    }

    public void handleRateRecipe(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("rate_recipe.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - Rate");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleChangeRecipe(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("search_recipe_to_change.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - Search the recipe you want to change");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

}
