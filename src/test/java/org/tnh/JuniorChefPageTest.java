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
class JuniorChefPageTest {

    private final String USERNAME = "Edward26";
    private final String RECIPE_NAME = "Chicken";
    private final String CALORIES = "300";
    private final String TIME = "55";
    private final String INSTRUCTIONS = "https://www.ambitiouskitchen.com/the-best-chicken-soup-recipe/";

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
        String FIRST_NAME = "Edward";
        String LAST_NAME = "Rosco";
        String EMAIL = "edward@yahoo.com";
        String PASSWORD = "Edw@rd62";
        String ROLE = "Junior Chef";
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("junior_chef.fxml")));
        primaryStage.setTitle("Junior Chef");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testSearch(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#searchMessage").queryText()).hasText("Complete all fields!");

        robot.clickOn("#search");
        robot.write(RECIPE_NAME);

        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#searchMessage").queryText()).hasText("Could not find recipes!");

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#enter_button");
        FxAssert.verifyThat(robot.window("Junior Chef - Search"), WindowMatchers.isShowing());
    }

    @Test
    void testShowRecipes(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#recipes_button");
        assertThat(robot.lookup("#showMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#recipes_button");
        FxAssert.verifyThat(robot.window("Junior Chef - List of recipes"), WindowMatchers.isShowing());
    }

    @Test
    void testShowSavedRecipes(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException, RecipeAlreadySavedException {
        robot.clickOn("#saved_recipes_button");
        assertThat(robot.lookup("#savedMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);
        RecipeService.addAdmirer(RECIPE_NAME, USERNAME);

        robot.clickOn("#saved_recipes_button");
        FxAssert.verifyThat(robot.window("Junior Chef - Saved recipes"), WindowMatchers.isShowing());
    }

    @Test
    void testSaveRecipe(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#save_recipe_button");
        assertThat(robot.lookup("#saveMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#save_recipe_button");
        FxAssert.verifyThat(robot.window("Junior Chef - Save"), WindowMatchers.isShowing());
    }

    @Test
    void testRateRecipe(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#rate_recipe_button");
        assertThat(robot.lookup("#rateMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#rate_recipe_button");
        FxAssert.verifyThat(robot.window("Junior Chef - Rate"), WindowMatchers.isShowing());
    }

    @Test
    void testLogout(FxRobot robot) {
        robot.clickOn("#logout_button");

        FxAssert.verifyThat(robot.window("Confirmation"), WindowMatchers.isShowing());

        robot.clickOn("Yes");

        FxAssert.verifyThat(robot.window("Cooking-Recipes-Application"), WindowMatchers.isShowing());
    }

}