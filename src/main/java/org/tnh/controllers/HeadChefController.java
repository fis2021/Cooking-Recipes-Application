package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tnh.exceptions.CouldNotFindRecipeException;
import org.tnh.exceptions.EmptyDataBaseException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

import java.io.IOException;
import java.util.Objects;

public class HeadChefController {

    private Stage stage;
    private Parent root;

    @FXML
    private Text ownedMessage;
    @FXML
    private Text showMessage;
    @FXML
    private Text rateMessage;
    @FXML
    private Text modifyMessage;
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
        try {
            RecipeService.emptyCreatedRecipesDataBase(LoggedUser.getLoggedUser().getUsername());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("own_recipes.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Head Chef - List of owned recipes");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            ownedMessage.setText(e.getMessage());
        }
    }

    public void handleShowRecipesAction(ActionEvent event) throws Exception {
        try {
            RecipeService.emptyDataBase();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("list_recipes.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Head Chef - List of recipes");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            showMessage.setText(e.getMessage());
        }
    }

    public void handleLogoutAction(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Are you sure you want to log out?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("Yes")) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Cooking-Recipes-Application");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        }
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
        try {
            RecipeService.emptyDataBase();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("rate_recipe.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Head Chef - Rate");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            rateMessage.setText(e.getMessage());
        }
    }

    public void handleChangeRecipe(ActionEvent event) throws Exception {
        try {
            RecipeService.emptyCreatedRecipesDataBase(LoggedUser.getLoggedUser().getUsername());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("search_recipe_to_change.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Head Chef - Search the recipe you want to change");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            modifyMessage.setText(e.getMessage());
        }
    }

}
