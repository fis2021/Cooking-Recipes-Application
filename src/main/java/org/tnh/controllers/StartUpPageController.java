package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tnh.exceptions.*;
import org.tnh.model.LoggedUser;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;
import java.util.Objects;

public class StartUpPageController {

    private Stage stage;
    private Parent root;

    @FXML
    private Text showMessage;
    @FXML
    private Text searchMessage;
    @FXML
    private Text loginMessage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField search;

    private static String searchValue;

    public static String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue () {
        searchValue = search.getText();
    }

    public void handleCreateAccountAction(ActionEvent event) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("register.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Registration");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public void handleLoginAction(ActionEvent event) throws Exception
    {
        try {
            UserService.loginUncompletedFields(username.getText(), password.getText());
            LoggedUser.setLoggedUser(UserService.loggedUser(username.getText(), password.getText()));
            if (UserService.getUserRole(username.getText()).equals("Junior Chef")) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("junior_chef.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Junior Chef");
            } else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("head_chef.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Head Chef");
            }
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch(UncompletedFieldsException | InvalidUsernameException | InvalidPasswordException e) {
            loginMessage.setText(e.getMessage());
        }
    }

    public void handleShowRecipes(ActionEvent event) throws Exception {
        try {
            RecipeService.emptyDataBase();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("list_recipes.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("List of recipes");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (EmptyDataBaseException e) {
            showMessage.setText(e.getMessage());
        }
    }

    public void handleSearch(ActionEvent event) throws Exception {
        try {
            RecipeService.uncompletedNameField(search.getText());
            RecipeService.couldNotFindSimilarRecipeNames(search.getText());
            setSearchValue();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("search.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Search");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (UncompletedFieldsException | CouldNotFindRecipeException e) {
            searchMessage.setText(e.getMessage());
        }
    }

}
