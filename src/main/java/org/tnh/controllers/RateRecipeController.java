package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

public class RateRecipeController extends AbstractGoBackController {

    @FXML
    private TextField recipe, rating;

    public void handleRate() {
        RecipeService.addRating(recipe.getText(), rating.getText(), LoggedUser.getLoggedUser().getUsername());
    }

}