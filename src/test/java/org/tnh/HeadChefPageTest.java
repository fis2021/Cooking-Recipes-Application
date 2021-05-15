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
import org.tnh.exceptions.RecipeAlreadyExistsException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.model.LoggedUser;
import org.tnh.model.User;
import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;


@SuppressWarnings("all")
@ExtendWith(ApplicationExtension.class)
class HeadChefPageTest {

    private final String FIRST_NAME = "Marius";
    private final String LAST_NAME = "Ardelean";
    private final String EMAIL = "marius@yahoo.com";
    private final String USERNAME = "Marius";
    private final String PASSWORD = "Marius";
    private final String CONFIRM_PASSWORD = "Marius";
    private final String ROLE = "Head Chef";

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

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("head_chef.fxml")));
        primaryStage.setTitle("Head Chef");
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
        FxAssert.verifyThat(robot.window("Head Chef - Search"), WindowMatchers.isShowing());
    }

    @Test
    void RecipeCreationTest(FxRobot robot){
        robot.clickOn("#create_button");
        FxAssert.verifyThat(robot.window("Head Chef - Create recipe"), WindowMatchers.isShowing());
    }

    @Test
    void testShowOwnRecipes(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException{
        robot.clickOn("#owned_recipes_button");
        assertThat(robot.lookup("#ownedMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#owned_recipes_button");
        FxAssert.verifyThat(robot.window("Head Chef - List of owned recipes"), WindowMatchers.isShowing());
    }

    @Test
    void testShowRecipes(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#recipes_button");
        assertThat(robot.lookup("#showMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#recipes_button");
        FxAssert.verifyThat(robot.window("Head Chef - List of recipes"), WindowMatchers.isShowing());
    }

    @Test
    void testRateRecipe(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#rate_recipe_button");
        assertThat(robot.lookup("#rateMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME + 1, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#rate_recipe_button");
        FxAssert.verifyThat(robot.window("Head Chef - Rate"), WindowMatchers.isShowing());
    }

    @Test
    void testChangeRecipe(FxRobot robot) throws RecipeAlreadyExistsException, UncompletedFieldsException {
        robot.clickOn("#change_recipe_button");
        assertThat(robot.lookup("#modifyMessage").queryText()).hasText("There are no recipes");

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);

        robot.clickOn("#change_recipe_button");
        FxAssert.verifyThat(robot.window("Head Chef - Search the recipe you want to change"), WindowMatchers.isShowing());
    }

        @Test
    void testLogout(FxRobot robot) {
        robot.clickOn("#logout_button");

        FxAssert.verifyThat(robot.window("Confirmation"), WindowMatchers.isShowing());

        robot.clickOn("Yes");

        FxAssert.verifyThat(robot.window("Cooking-Recipes-Application"), WindowMatchers.isShowing());
    }
}