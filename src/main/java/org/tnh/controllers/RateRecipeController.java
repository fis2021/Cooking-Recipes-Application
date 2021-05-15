package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import org.tnh.exceptions.*;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

public class RateRecipeController extends AbstractWindowViewController {

    @FXML
    private Text recipeMessage;
    @FXML
    private TextField recipe, rating;

    public void handleRate() {
        try
        {
            RecipeService.emptyRatingFields(recipe.getText(), rating.getText());
            RecipeService.couldNotFindThisExactRecipeName(recipe.getText());
            RecipeService.invalidGrade(rating.getText());
            RecipeService.addRating(recipe.getText(), rating.getText(), LoggedUser.getLoggedUser().getUsername());
            recipeMessage.setText("Recipe graded successfully!");
        } catch(RecipeAlreadyRatedException | CouldNotFindRecipeException | UncompletedFieldsException | InvalidGradeException | YouCantRateYourRecipeException e){
            recipeMessage.setText(e.getMessage());
        }
    }

}
