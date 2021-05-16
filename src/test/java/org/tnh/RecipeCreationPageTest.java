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
import org.tnh.model.LoggedUser;
import org.tnh.model.User;
import org.tnh.services.FileSystemService;
import org.tnh.services.RecipeService;
import org.tnh.services.UserService;

import java.util.Objects;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class RecipeCreationPageTest {

    private final String FIRST_NAME = "Marius";
    private final String LAST_NAME = "Ardeen";
    private final String EMAIL = "marius@yahoo.com";
    private final String USERNAME = "Marius";
    private final String PASSWORD = "Marius";
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

    @SuppressWarnings("unused")
    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("create_recipe.fxml")));
        primaryStage.setTitle("Head Chef - Create recipe");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Test
    void testCanGoToPreviousPage(FxRobot robot) {
        robot.clickOn("#back_button");
        FxAssert.verifyThat(robot.window("Head Chef"), WindowMatchers.isShowing());
    }

    @Test
    void testRecipeCreation(FxRobot robot) {
        robot.clickOn("#register_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Complete all fields!");

        robot.clickOn("#name");
        robot.write(RECIPE_NAME);

        robot.clickOn("#calories");
        robot.write(CALORIES);

        robot.clickOn("#time");
        robot.write(TIME);

        robot.clickOn("#instructions");
        robot.write(INSTRUCTIONS);

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Recipe added successfully!");
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(1);

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText(String.format("A recipe with the name %s already exists!", RECIPE_NAME));

        robot.clickOn("#name");
        robot.write("2");

        robot.clickOn("#register_button");
        assertThat(robot.lookup("#recipeMessage").queryText()).hasText("Recipe added successfully!");
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(2);
    }
}