package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tnh.exceptions.*;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class RecipeCreationController {

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
            RecipeService.addRecipe(name.getText(), calories.getText(), time.getText(), instructions.getText());
            recipeMessage.setText("Recipe added successfully!");
        } catch(UncompletedFieldsException | RecipeAlreadyExistsException e) {
            recipeMessage.setText(e.getMessage());
        }
    }

    public void handleBackAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("head.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }
}
