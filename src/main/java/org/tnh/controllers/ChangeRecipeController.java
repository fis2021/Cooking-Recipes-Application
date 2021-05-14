package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

public class ChangeRecipeController extends AbstractGoBackController {

    @FXML
    private TextField name, calories, time, instructions;

    public void handleModifyAction() {
        RecipeService.modifyRecipe(name.getText(), calories.getText(), time.getText(), instructions.getText(), LoggedUser.getLoggedUser().getUsername());
    }

    public void handleDeleteAction() {
        RecipeService.deleteRecipe(name.getText());
    }

}
