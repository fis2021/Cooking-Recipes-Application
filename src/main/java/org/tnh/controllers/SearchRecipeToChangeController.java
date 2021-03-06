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
import org.tnh.exceptions.CouldNotFindRecipeException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.exceptions.YouCantModifyThisRecipe;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;

import java.util.Objects;

public class SearchRecipeToChangeController extends AbstractWindowViewController {

    @FXML
    private Text searchMessage;
    @FXML
    private TextField search_recipe;

    private static String searchValue;

    public static String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue () {
        searchValue = search_recipe.getText();
    }

    public static void setSearchValue (String value) { searchValue = value; }

    public void handleEnterAction(ActionEvent event) throws Exception {
        try {
            RecipeService.uncompletedNameField(search_recipe.getText());
            RecipeService.couldNotFindThisExactRecipeName(search_recipe.getText());
            RecipeService.ownedRecipeFound(search_recipe.getText(), LoggedUser.getLoggedUser().getUsername());
            setSearchValue();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("change_recipe.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Head Chef - Change Recipe");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch(CouldNotFindRecipeException | UncompletedFieldsException | YouCantModifyThisRecipe e){
            searchMessage.setText(e.getMessage());
        }
    }

}
