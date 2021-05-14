package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import org.tnh.exceptions.CouldNotFindRecipeException;
import org.tnh.exceptions.RecipeAlreadySavedException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

public class SaveRecipeController extends AbstractWindowViewController {

    @FXML
    private Text searchMessage;
    @FXML
    private TextField search;

    public void handleSave() {
        try{
            RecipeService.uncompletedNameField(search.getText());
            RecipeService.couldNotFindThisRecipe(search.getText());
            RecipeService.addAdmirer(search.getText(), LoggedUser.getLoggedUser().getUsername());
            searchMessage.setText("The recipe has been saved to favorites!");
        } catch(CouldNotFindRecipeException | UncompletedFieldsException | RecipeAlreadySavedException e){
            searchMessage.setText(e.getMessage());
        }
    }

}
