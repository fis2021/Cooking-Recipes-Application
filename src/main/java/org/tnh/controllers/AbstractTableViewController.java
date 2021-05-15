package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import org.tnh.model.Recipe;

public abstract class AbstractTableViewController extends AbstractWindowViewController {

    @FXML
    protected TableColumn<Recipe, String> recipeName, recipeCalories, recipeTime, recipeInstructions, recipeRating;

    protected void initVars() {
        recipeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        recipeCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        recipeTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        recipeInstructions.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        recipeRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    }

}
