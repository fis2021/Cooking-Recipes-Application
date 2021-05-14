package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.tnh.services.RecipeService;

public class ChangeRecipeController extends AbstractController {

    @FXML
    private TextField name, calories, time, instructions;

    public void handleSaveAction() {
        RecipeService.modifyRecipe(name.getText(), calories.getText(), time.getText(), instructions.getText(), SearchRecipeToChangeController.getSearchValue());
    }

    public void handleDeleteAction() {
        RecipeService.deleteRecipe(SearchRecipeToChangeController.getSearchValue());
    }

}
