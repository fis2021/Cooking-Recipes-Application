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
import org.tnh.exceptions.EmptyDataBaseException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class JuniorChefController {

    private Stage stage;
    private Parent root;

    @FXML
    private Text savedMessage;
    @FXML
    private Text showMessage;
    @FXML
    private Text saveMessage;
    @FXML
    private Text rateMessage;
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

    public void handleLogout(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cooking-Recipes-Application");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleSavedRecipes(ActionEvent event) throws Exception {
        try {
            RecipeService.emptySavedDataBase(LoggedUser.getLoggedUser().getUsername());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("saved_recipes.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Junior Chef - Saved recipes");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
           savedMessage.setText(e.getMessage());
        }
    }

    public void handleShowRecipes(ActionEvent event) throws Exception {
        try {
            RecipeService.emptyDataBase();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("list_recipes.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Junior Chef - List of recipes");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            showMessage.setText(e.getMessage());
        }
    }

    public void handleSearch(ActionEvent event) throws Exception {
        try {
            RecipeService.uncompletedNameField(search.getText());
            RecipeService.couldNotFindRecipe(search.getText());
            setSearchValue();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("searchJuniorChef.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Junior Chef - Search");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (UncompletedFieldsException | CouldNotFindRecipeException e) {
            searchMessage.setText(e.getMessage());
        }
    }

    public void handleSaveRecipe(ActionEvent event) throws Exception {
        try {
            RecipeService.emptyDataBase();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("save_recipe.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Junior Chef - Save");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            saveMessage.setText(e.getMessage());
        }
    }

    public void handleRateRecipe(ActionEvent event) throws Exception {
        try {
            RecipeService.emptyDataBase();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("rate_recipe.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Junior Chef - Rate");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            rateMessage.setText(e.getMessage());
        }
    }

}
