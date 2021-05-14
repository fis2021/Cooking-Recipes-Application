package org.tnh.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.tnh.model.LoggedUser;
import org.tnh.model.Recipe;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class OwnRecipesController {

    @FXML
    private TableColumn<Recipe, String> recipeName, recipeCalories, recipeTime, recipeInstructions;
    @FXML
    private TableView<Recipe> recipesTableView;

    public void initialize() {
        setTable();
    }

    public void handleBackAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("head.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void setTable() {
        recipeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        recipeCalories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        recipeTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        recipeInstructions.setCellValueFactory(new PropertyValueFactory<>("Instructions"));
        ObservableList<Recipe> recipes_obs = FXCollections.observableArrayList();
        recipes_obs.addAll(Objects.requireNonNull(RecipeService.populateDataCreatedRecipesList(LoggedUser.getLoggedUser().getUsername())));
        recipesTableView.setItems(recipes_obs);
    }
}
