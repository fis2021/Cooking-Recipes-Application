package org.tnh.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;
import org.tnh.exceptions.ConfirmPasswordAndPasswordNotEqualException;
import org.tnh.exceptions.PasswordNoUpperCaseException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.exceptions.UsernameAlreadyExistsException;
import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class RegistrationTest {

    private final String FIRST_NAME = "Nicu";
    private final String LAST_NAME = "Ardelean";
    private final String EMAIL = "nicu@yahoo.com";
    private final String USERNAME = "Nicu";
    private final String PASSWORD = "Nicu";
    private final String CONFIRM_PASSWORD = "Nicu";
    private final String ROLE = "Head Chef";

    @BeforeAll
    static void beforeAll() {
        FileSystemService.APPLICATION_FOLDER = ".test-cooking-recipes";
        FileSystemService.initDirectory();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.closeDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("register.fxml")));
        primaryStage.setTitle("Registration");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testRegister(FxRobot robot) throws UsernameAlreadyExistsException, UncompletedFieldsException, PasswordNoUpperCaseException, ConfirmPasswordAndPasswordNotEqualException {
        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Complete all fields!");

        robot.clickOn("#firstName");
        robot.write(FIRST_NAME);
        robot.clickOn("#lastName");
        robot.write(LAST_NAME);
        robot.clickOn("#email");
        robot.write(EMAIL);
        robot.clickOn("#username");
        robot.write(USERNAME);
        robot.clickOn("#password");
        robot.write(PASSWORD);
        robot.clickOn("#confirmPassword");
        robot.write(CONFIRM_PASSWORD);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText(String.format("An account with the username %s already exists!", USERNAME));

        robot.clickOn("#username");
        robot.eraseText(USERNAME.length());
        robot.write(USERNAME + "1");

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("First name is not unique!");

        robot.clickOn("#firstName");
        robot.eraseText(FIRST_NAME.length());
        robot.write(FIRST_NAME + "1");

        robot.clickOn("#password");
        robot.eraseText(PASSWORD.length());
        robot.write("marius");

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("The password must have one upper case!");

        robot.clickOn("#password");
        robot.eraseText("marius".length());
        robot.write("Marius12");

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("The 2 password fields are not the same!");

        robot.clickOn("#confirmPassword");
        robot.eraseText(CONFIRM_PASSWORD.length());
        robot.write("Marius12");

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#registrationMessage").queryText()).hasText("Account created successfully!");

        robot.clickOn("#back_button");
        FxAssert.verifyThat(robot.window("Cooking-Recipes-Application"), WindowMatchers.isShowing());
    }
}