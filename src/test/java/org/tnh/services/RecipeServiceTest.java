package org.tnh.services;

import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.tnh.exceptions.*;
import org.tnh.model.Recipe;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class RecipeServiceTest {

    private final String USERNAME = "Edward26";
    private final String NAME = "Chicken soup";
    private final String CALORIES = "300";
    private final String TIME = "55";
    private final String INSTRUCTIONS = "https://www.ambitiouskitchen.com/the-best-chicken-soup-recipe/";
    private final String GRADE = "5";

    @BeforeAll
    static void beforeAll() {
        FileSystemService.APPLICATION_FOLDER = ".test-cooking-recipes";
        FileSystemService.initDirectory();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        RecipeService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        RecipeService.closeDatabase();
    }

    @Test
    void testDatabaseIsInitializedAndNoRecipeIsPersisted() {
        assertThat(RecipeService.getAllRecipes()).isNotNull();
        assertThat(RecipeService.getAllRecipes()).isEmpty();
    }
    @Test
    void testRecipeIsAddedToDatabase() throws RecipeAlreadyExistsException, UncompletedFieldsException {
        RecipeService.addRecipe(USERNAME, NAME, CALORIES, TIME, INSTRUCTIONS);
        assertThat(RecipeService.getAllRecipes()).isNotEmpty();
        assertThat(RecipeService.getAllRecipes()).size().isEqualTo(1);
        Recipe recipe = RecipeService.getAllRecipes().get(0);
        assertThat(recipe).isNotNull();
        assertThat(recipe.getUsername()).isEqualTo(USERNAME);
        assertThat(recipe.getName()).isEqualTo(NAME);
        assertThat(recipe.getCalories()).isEqualTo(CALORIES);
        assertThat(recipe.getTime()).isEqualTo(TIME);
        assertThat(recipe.getInstructions()).isEqualTo(INSTRUCTIONS);
    }

    @Test
    void testRecipeCanNotBeAddedTwice() {
        assertThrows(RecipeAlreadyExistsException.class, () -> {
            RecipeService.addRecipe(USERNAME, NAME, CALORIES, TIME, INSTRUCTIONS);
            RecipeService.addRecipe(USERNAME, NAME, CALORIES, TIME, INSTRUCTIONS);
        });
    }

    @Test
    void testAllFieldsAreCompleted() {
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.addRecipe(USERNAME, "", CALORIES, TIME, INSTRUCTIONS));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.addRecipe(USERNAME, NAME, "", TIME, INSTRUCTIONS));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.addRecipe(USERNAME, NAME, CALORIES, "", INSTRUCTIONS));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.addRecipe(USERNAME, NAME, CALORIES, TIME, ""));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.addRecipe(USERNAME, "", CALORIES, TIME, ""));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.addRecipe(USERNAME, "", "", "", ""));
    }

    @Test
    void testNameFieldIsCompleted() {
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.uncompletedNameField(""));
    }

    @Test
    void testSimilarRecipesCanBeFound() throws RecipeAlreadyExistsException, UncompletedFieldsException {
        RecipeService.addRecipe(USERNAME, "Chicken", CALORIES, TIME, INSTRUCTIONS);
        assertThrows(CouldNotFindRecipeException.class, () ->
                RecipeService.couldNotFindSimilarRecipeNames("Pork"));
    }

    @Test
    void testExactRecipesCanBeFound() throws RecipeAlreadyExistsException, UncompletedFieldsException {
        RecipeService.addRecipe(USERNAME, "Chicken", CALORIES, TIME, INSTRUCTIONS);
        assertThrows(CouldNotFindRecipeException.class, () ->
                RecipeService.couldNotFindThisExactRecipeName("Pork"));
    }

    @Test
    void testRecipesDatabaseIsEmpty() {
        assertThrows(EmptyDataBaseException.class, RecipeService::emptyDataBase);
    }

    @Test
    void testSavedRecipesDatabaseIsEmpty() {
        assertThrows(EmptyDataBaseException.class, () ->
                RecipeService.emptySavedDataBase(USERNAME));
    }

    @Test
    void testCreatedRecipesDatabaseIsEmpty() {
        assertThrows(EmptyDataBaseException.class, () ->
                RecipeService.emptyCreatedRecipesDataBase(USERNAME));
    }

    @Test
    void testRatingFieldIsCompleted() {
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.emptyRatingFields(NAME, ""));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.emptyRatingFields("", GRADE));
        assertThrows(UncompletedFieldsException.class, () ->
                RecipeService.emptyRatingFields("", ""));
    }

}