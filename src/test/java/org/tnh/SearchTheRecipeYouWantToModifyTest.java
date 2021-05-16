package org.tnh;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import org.tnh.exceptions.*;
import org.tnh.model.LoggedUser;
import org.tnh.model.User;
import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class SearchTheRecipeYouWantToModifyTest {

    private final String USERNAME = "Marius";

    @BeforeAll
    static void beforeAll() {
        FileSystemService.APPLICATION_FOLDER = ".test-cooking-recipes";
        FileSystemService.initDirectory();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        RecipeService.initDatabase();
        String FIRST_NAME = "Marius";
        String LAST_NAME = "Ardeen";
        String EMAIL = "marius@yahoo.com";
        String PASSWORD = "Marius";
        String ROLE = "Head Chef";
        LoggedUser.setLoggedUser(new User(FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, ROLE));
    }

    @AfterEach
    void tearDown() {
        UserService.closeDatabase();
        RecipeService.closeDatabase();
    }

    @SuppressWarnings("unused")
    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("search_recipe_to_change.fxml")));
        primaryStage.setTitle("Head Chef - Search the recipe you want to change");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testSearchRecipeYouWantToModify(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#searchMessage").queryText()).hasText("Complete all fields!");

        robot.clickOn("#search_recipe");
        String RECIPE_NAME = "Chicken";
        robot.write(RECIPE_NAME);

        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#searchMessage").queryText()).hasText("Could not find recipes!");

        String CALORIES = "300";
        String TIME = "55";
        String INSTRUCTIONS = "https://www.ambitiouskitchen.com/the-best-chicken-soup-recipe/";
        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME + 1, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#search_recipe");
        robot.eraseText(RECIPE_NAME.length());
        robot.write(RECIPE_NAME + 1);
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#searchMessage").queryText()).hasText("You can't modify recipes that you don't own!");

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#search_recipe");
        robot.eraseText(RECIPE_NAME.length() + 1);
        robot.write(RECIPE_NAME);
        robot.clickOn("#enter_button");
        FxAssert.verifyThat(robot.window("Head Chef - Change Recipe"), WindowMatchers.isShowing());
    }

    @Test
    void testCanGoToPreviousPage(FxRobot robot) {
        robot.clickOn("#back_button");
        FxAssert.verifyThat(robot.window("Head Chef"), WindowMatchers.isShowing());
    }
}