package org.tnh;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
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


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;



@ExtendWith(ApplicationExtension.class)
class StartupPageTest {

    private final String FIRST_NAME = "Edward";
    private final String LAST_NAME = "Rosco";
    private final String EMAIL = "edward@yahoo.com";
    private final String USERNAME = "Edward26";
    private final String PASSWORD = "Edw@rd62";
    private final String CONFIRM_PASSWORD = "Edw@rd62";
    private final String ROLE = "Head Chef";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-cooking-recipes";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        RecipeService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.closeDatabase();
        RecipeService.closeDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("startup_page.fxml")));
        primaryStage.setTitle("Cooking-Recipes-Application");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testLogin(FxRobot robot) throws ConfirmPasswordAndPasswordNotEqualException, UsernameAlreadyExistsException, PasswordNoUpperCaseException, UncompletedFieldsException {
        robot.clickOn("#startup_login_button");
        assertThat(robot.lookup("#startup_login_message").queryText()).hasText("Complete all fields!");

        robot.clickOn("#startup_username");
        robot.write(USERNAME);
        robot.clickOn("#startup_password");
        robot.write(PASSWORD);

        robot.clickOn("#startup_login_button");
        assertThat(robot.lookup("#startup_login_message").queryText()).hasText("Invalid username");

        UserService.addUser(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD + 1, CONFIRM_PASSWORD + 1, ROLE);

        robot.clickOn("#startup_login_button");
        assertThat(robot.lookup("#startup_login_message").queryText()).hasText("Invalid password");

        robot.clickOn("#startup_password");
        robot.write("1");

        robot.clickOn("#startup_login_button");
        FxAssert.verifyThat(robot.window("Head Chef"), WindowMatchers.isShowing());
    }

}