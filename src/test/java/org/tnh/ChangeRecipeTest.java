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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import org.testfx.matcher.base.WindowMatchers;
import org.tnh.controllers.SearchRecipeToChangeController;
import org.tnh.exceptions.RecipeAlreadyExistsException;
import org.tnh.exceptions.UncompletedFieldsException;
import org.tnh.model.LoggedUser;
import org.tnh.model.Recipe;
import org.tnh.model.User;
import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ChangeRecipeTest {

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
        String USERNAME = "Marius";
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("change_recipe.fxml")));
        primaryStage.setTitle("Head Chef - Change Recipe");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testCanGoToPreviousPage(FxRobot robot) {
        robot.clickOn("#back_button");
        FxAssert.verifyThat(robot.window("Head Chef - Search the recipe you want to change"), WindowMatchers.isShowing());
    }

    @Test
    void testSaveChanges(FxRobot robot) throws UncompletedFieldsException, RecipeAlreadyExistsException {
        String RECIPE_NAME = "Chicken";
        String CALORIES = "300";
        String TIME = "100";
        String INSTRUCTIONS = "https://www.ambitiouskitchen.com/the-best-chicken-soup-recipe/";
        String USERNAME = "Marius";

        RecipeService.addRecipe(USERNAME, RECIPE_NAME+1, CALORIES, TIME, INSTRUCTIONS);
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(1);

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(2);
        SearchRecipeToChangeController.setSearchValue(RECIPE_NAME);

        robot.clickOn("#name");
        robot.write(RECIPE_NAME+1);

        robot.clickOn("#calories");
        robot.write(CALORIES);

        robot.clickOn("#time");
        robot.write(TIME+1);

        robot.clickOn("#instructions");
        robot.write(INSTRUCTIONS);

        robot.clickOn("#save_button");
        Assertions.assertThat(robot.lookup("#recipeMessage").queryText()).hasText(String.format("A recipe with the name %s already exists!", RECIPE_NAME+1));

        robot.clickOn("#name");
        robot.eraseText(RECIPE_NAME.length()+1);
        robot.write(RECIPE_NAME+2);

        robot.clickOn("#save_button");
        FxAssert.verifyThat(robot.window("Confirmation"), WindowMatchers.isShowing());
        robot.clickOn("#No_Save");
        FxAssert.verifyThat(robot.window("Head Chef - Change Recipe"), WindowMatchers.isShowing());
        robot.clickOn("#save_button");
        FxAssert.verifyThat(robot.window("Confirmation"), WindowMatchers.isShowing());
        robot.clickOn("#Yes_Save");
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(2);

        Recipe recipe = RecipeService.getAllRecipes().get(1);
        assertThat(recipe.getName()).isEqualTo(RECIPE_NAME + 2);
        assertThat(recipe.getTime()).isEqualTo(TIME + 1);
        assertThat(recipe.getCalories()).isEqualTo(CALORIES);

        robot.clickOn("#OK");
        FxAssert.verifyThat(robot.window("Head Chef - Search the recipe you want to change"), WindowMatchers.isShowing());
    }

    @Test
    void testDeleteRecipe(FxRobot robot) throws UncompletedFieldsException, RecipeAlreadyExistsException {
        String RECIPE_NAME = "Chicken";
        String CALORIES = "300";
        String TIME = "100";
        String INSTRUCTIONS = "https://www.ambitiouskitchen.com/the-best-chicken-soup-recipe/";
        String USERNAME = "Marius";

        RecipeService.addRecipe(USERNAME, RECIPE_NAME, CALORIES, TIME, INSTRUCTIONS);
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(1);
        SearchRecipeToChangeController.setSearchValue(RECIPE_NAME);

        robot.clickOn("#delete_button");
        FxAssert.verifyThat(robot.window("Confirmation"), WindowMatchers.isShowing());
        robot.clickOn("#No_Delete");
        FxAssert.verifyThat(robot.window("Head Chef - Change Recipe"), WindowMatchers.isShowing());
        robot.clickOn("#delete_button");
        FxAssert.verifyThat(robot.window("Confirmation"), WindowMatchers.isShowing());
        robot.clickOn("#Yes_Delete");
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(0);
        robot.clickOn("#OK");
        FxAssert.verifyThat(robot.window("Head Chef - Search the recipe you want to change"), WindowMatchers.isShowing());
    }
}