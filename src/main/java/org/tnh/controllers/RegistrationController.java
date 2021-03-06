package org.tnh.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tnh.exceptions.*;
import org.tnh.services.UserService;
import java.util.Objects;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField username;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private ChoiceBox<String> role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Junior Chef", "Head Chef");
        role.setValue("Junior Chef");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(firstName.getText(), lastName.getText(), email.getText(), username.getText(), password.getText(), confirmPassword.getText(), role.getValue());
            registrationMessage.setText("Account created successfully!");
        } catch(UncompletedFieldsException | UsernameAlreadyExistsException | PasswordNoUpperCaseException | ConfirmPasswordAndPasswordNotEqualException e) {
            registrationMessage.setText(e.getMessage());
        }
    }

    public void handleBackFirstPageAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Cooking-Recipes-Application");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }
}
