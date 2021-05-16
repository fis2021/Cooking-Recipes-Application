package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tnh.exceptions.RecipeAlreadyExistsException;
import org.tnh.services.RecipeService;

import java.io.IOException;
import java.util.Objects;

public class ChangeRecipeController {

    private Parent root;
    private Stage stage;

    @FXML
    private Text recipeMessage;
    @FXML
    private TextField name, calories, time, instructions;

    public void handleSaveAction(ActionEvent event) throws Exception{
        try {
            if (!name.getText().equals("") && !name.getText().equals(SearchRecipeToChangeController.getSearchValue())) RecipeService.recipeNameAlreadyUsed(name.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.getDialogPane().lookupButton(ButtonType.YES).setId("Yes_Save");
            alert.getDialogPane().lookupButton(ButtonType.NO).setId("No_Save");
            alert.setHeaderText("Are you sure you want to modify this recipe?");
            alert.showAndWait();
            if (alert.getResult().getText().equals("Yes")) {
                RecipeService.modifyRecipe(name.getText(), calories.getText(), time.getText(), instructions.getText(), SearchRecipeToChangeController.getSearchValue());
                Alert alert2 = setRoot(event);
                alert2.setHeaderText("The recipe has been modified");
                alert2.showAndWait();
            }
        } catch (RecipeAlreadyExistsException e) {
            recipeMessage.setText(e.getMessage());
        }
    }

    private Alert setRoot(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("search_recipe_to_change.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - Search the recipe you want to change");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.getDialogPane().lookupButton(ButtonType.OK).setId("OK");
        return alert;
    }

    public void handleDeleteAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().lookupButton(ButtonType.YES).setId("Yes_Delete");
        alert.getDialogPane().lookupButton(ButtonType.NO).setId("No_Delete");
        alert.setHeaderText("Are you sure you want to permanently delete this recipe?");
        alert.showAndWait();
            if(alert.getResult().getText().equals("Yes")) {
                RecipeService.deleteRecipe(SearchRecipeToChangeController.getSearchValue());
                Alert alert2 = setRoot(event);
                alert2.setHeaderText("The recipe has been deleted!");
                alert2.showAndWait();
            }
    }

    public void handleBackAction(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("search_recipe_to_change.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Head Chef - Search the recipe you want to change");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

}
