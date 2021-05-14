package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

public class SaveRecipeController extends AbstractController {

    @FXML
    private TextField search;

    public void handleSave() {
        RecipeService.addAdmirer(search.getText(), LoggedUser.getLoggedUser().getUsername());
    }

}
