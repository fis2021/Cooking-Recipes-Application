package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.tnh.model.LoggedUser;
import org.tnh.services.UserService;

import java.util.Objects;

public abstract class AbstractChefController {

    protected Stage stage;
    protected Parent root;

    public void handleLogoutAction(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().lookupButton(ButtonType.YES).setId("Yes_logout");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("Yes")) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Cooking-Recipes-Application");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        }
    }

    public void handleDeleteAccountAction(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.getDialogPane().lookupButton(ButtonType.YES).setId("Yes_delete");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.showAndWait();
        if(alert.getResult().getText().equals("Yes")) {
            UserService.deleteAccount(LoggedUser.getLoggedUser().getUsername());
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Cooking-Recipes-Application");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        }
    }

}
