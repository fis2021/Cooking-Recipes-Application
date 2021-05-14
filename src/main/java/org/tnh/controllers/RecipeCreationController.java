package org.tnh.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.tnh.exceptions.*;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

public class RecipeCreationController extends AbstractController {

    @FXML
    private Text recipeMessage;
    @FXML
    private TextField name;
    @FXML
    private TextField calories;
    @FXML
    private TextField time;
    @FXML
    private TextField instructions;

    @FXML
    public void handleRegisterAction() {
        try {
            RecipeService.addRecipe(LoggedUser.getLoggedUser().getUsername(), name.getText(), calories.getText(), time.getText(), instructions.getText());
            recipeMessage.setText("Recipe added successfully!");
        } catch(UncompletedFieldsException | RecipeAlreadyExistsException e) {
            recipeMessage.setText(e.getMessage());
        }
    }

}
