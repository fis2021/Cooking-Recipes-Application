package org.tnh;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
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
import org.tnh.model.Recipe;
import org.tnh.model.User;
import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class JuniorRateWindowTest {

    private final String FIRST_NAME = "Edward";
    private final String LAST_NAME = "Rosco";
    private final String EMAIL = "edward@yahoo.com";
    private final String USERNAME = "Edward26";
    private final String PASSWORD = "Edw@rd62";
    private final String ROLE = "Junior Chef";

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("rate_recipe.fxml")));
        primaryStage.setTitle("Junior Chef - Rate");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testAllFieldsAreCompleted(FxRobot robot) {
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Complete all fields!");

        robot.clickOn("#recipe");
        robot.write(RECIPE_NAME);

        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Complete all fields!");

        robot.clickOn("#recipe");
        robot.eraseText(RECIPE_NAME.length());

        robot.clickOn("#rating");
        robot.write("5");

        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Complete all fields!");
    }

    @Test
    void testCanGoToPreviousPage(FxRobot robot) {
        robot.clickOn("#back_button");
        FxAssert.verifyThat(robot.window("Junior Chef"), WindowMatchers.isShowing());
    }

    @Test
    void testRatingWasAddedSuccessfully(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#recipe");
        robot.write(RECIPE_NAME);
        robot.clickOn("#rating");
        robot.write("5");
        robot.clickOn("#enter_button");

        Recipe recipe = RecipeService.getAllRecipes().get(0);
        Assertions.assertThat(recipe.getRating()).isEqualTo("5.0");

        LoggedUser.setLoggedUser(new User(FIRST_NAME, LAST_NAME, EMAIL, USERNAME + 2, PASSWORD, ROLE));

        robot.clickOn("#rating");
        robot.eraseText(1);
        robot.write("8");
        robot.clickOn("#enter_button");

        recipe = RecipeService.getAllRecipes().get(0);
        Assertions.assertThat(recipe.getRating()).isEqualTo("6.5");
    }

    @Test
    void testSameUserCantRateSameRecipeTwice(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);
        robot.clickOn("#recipe");
        robot.write(RECIPE_NAME);
        robot.clickOn("#rating");
        robot.write("5");
        robot.clickOn("#enter_button");
        robot.eraseText(1);
        robot.write("8");
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("You already rated this recipe!");
    }

    @Test
    void testInvalidGrade(FxRobot robot) throws UncompletedFieldsException, RecipeAlreadyExistsException {
        RecipeService.addRecipe(USERNAME + "1", RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);
        robot.clickOn("#recipe");
        robot.write(RECIPE_NAME);
        robot.clickOn("#rating");
        robot.write("55");
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("You can only introduce grades from 1 to 10!");

        robot.clickOn("#rating");
        robot.eraseText(2);
        robot.write("5");
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Recipe graded successfully!");
    }

    @Test
    void testRecipeNotFound(FxRobot robot) throws UncompletedFieldsException, RecipeAlreadyExistsException {
        robot.clickOn("#recipe");
        robot.write(RECIPE_NAME);
        robot.clickOn("#rating");
        robot.write("5");
        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Could not find recipes!");

        RecipeService.addRecipe(USERNAME + "1", RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#enter_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Recipe graded successfully!");
    }
}