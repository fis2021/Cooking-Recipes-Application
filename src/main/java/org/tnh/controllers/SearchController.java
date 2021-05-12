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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.tnh.model.Recipe;
import org.tnh.services.RecipeService;
import java.util.Objects;

public class SearchController {

    @FXML
    private TableColumn<Recipe, String> recipeName, recipeCalories, recipeTime, recipeInstructions;
    @FXML
    private TableView<Recipe> recipesTableView;

    public void initialize() {
        setTable();
    }

    public void setTable() {
        recipeName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        recipeCalories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
        recipeTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        recipeInstructions.setCellValueFactory(new PropertyValueFactory<>("Instructions"));
        ObservableList<Recipe> recipes_obs = FXCollections.observableArrayList();
        for(var i : RecipeService.populateDataSearch(LoginController.getSearch())) {
            System.out.println(i.getName());
            recipes_obs.add(i);
        }
        recipesTableView.setItems(recipes_obs);
    }

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
