package org.tnh.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tnh.model.LoggedUser;
import org.tnh.model.Recipe;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class SavedRecipesController extends AbstractGoBackController {

    @FXML
    private TableColumn<Recipe, String> recipeName, recipeCalories, recipeTime, recipeInstructions, recipeRating;
    @FXML
    private TableView<Recipe> recipesTableView;

    public void initialize() {
        setTable();
    }

    public void setTable() {
        recipeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        recipeCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        recipeTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        recipeInstructions.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        recipeRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ObservableList<Recipe> recipes_obs = FXCollections.observableArrayList();
        recipes_obs.addAll(Objects.requireNonNull(RecipeService.populateSavedRecipesList(LoggedUser.getLoggedUser().getUsername())));
        recipesTableView.setItems(recipes_obs);
    }

}
