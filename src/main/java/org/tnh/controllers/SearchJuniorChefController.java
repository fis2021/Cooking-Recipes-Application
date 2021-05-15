package org.tnh.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import org.tnh.model.Recipe;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class SearchJuniorChefController extends AbstractTableViewController {

    @FXML
    private TableView<Recipe> recipesTableView;

    public void initialize() {
        setTable();
    }

    public void setTable() {
        initVars();
        ObservableList<Recipe> recipes_obs = FXCollections.observableArrayList();
        recipes_obs.addAll(Objects.requireNonNull(RecipeService.populateDataSearch(JuniorChefController.getSearchValue())));
        recipesTableView.setItems(recipes_obs);
    }

}
