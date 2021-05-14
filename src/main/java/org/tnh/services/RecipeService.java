package org.tnh.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.tnh.exceptions.*;
import org.tnh.model.Recipe;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.tnh.services.FileSystemService.getPathToFile;

public class RecipeService {

    private static ObjectRepository<Recipe> recipeRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("recipes.db").toFile())
                .openOrCreate("test", "test");

        recipeRepository = database.getRepository(Recipe.class);
    }

    public static void addRecipe(String username, String name, String calories, String time, String instructions) throws UncompletedFieldsException, RecipeAlreadyExistsException {
        uncompletedFields(name, calories, time, instructions);
        checkRecipeDoesNotAlreadyExist(name);
        Recipe r = new Recipe(username, name, calories, time, instructions);
        UUID u = r.getRecipe_id();
        while (!checkIDisUnique(u)) {
            u = r.rand_UUID();
            checkIDisUnique(u);
        }
        recipeRepository.insert(r);
    }

    public static boolean checkIDisUnique(UUID u) {
        Cursor<Recipe> cursor = recipeRepository.find();
        for (Recipe recipe : cursor) {
            if (u.equals(recipe.getRecipe_id())) {
                return false;
            }
        }
        return true;
    }

    private static void checkRecipeDoesNotAlreadyExist(String name) throws RecipeAlreadyExistsException {
        for (Recipe recipe : recipeRepository.find()) {
            if (name.equals(recipe.getName())) {
                throw new RecipeAlreadyExistsException(name);
            }
        }
    }

    public static void uncompletedFields(String name, String calories, String time,  String instructions) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        Matcher matcher1 = pattern.matcher(name);
        Matcher matcher2 = pattern.matcher(calories);
        Matcher matcher3 = pattern.matcher(time);
        Matcher matcher4 = pattern.matcher(instructions);
        boolean matchFound1 = matcher1.find();
        boolean matchFound2 = matcher2.find();
        boolean matchFound3 = matcher3.find();
        boolean matchFound4 = matcher4.find();
        if(!matchFound1) throw new UncompletedFieldsException();
        if(!matchFound2) throw new UncompletedFieldsException();
        if(!matchFound3) throw new UncompletedFieldsException();
        if(!matchFound4) throw new UncompletedFieldsException();
    }

    public static void uncompletedNameField(String name) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        Matcher matcher1 = pattern.matcher(name);
        boolean matchFound1 = matcher1.find();
        if(!matchFound1) throw new UncompletedFieldsException();
    }

    public static ArrayList<Recipe> populateData() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            recipes.add(recipe);
        }
        return recipes.size() == 0 ? null : recipes;
    }

    public static ArrayList<Recipe> populateDataSearch(String name) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe.getName().toLowerCase().contains(name.toLowerCase())) {
                recipes.add(recipe);
            }
        }
        return recipes.size() == 0 ? null : recipes;
    }

    public static ArrayList<Recipe> populateDataCreatedRecipesList(String username) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            if (username.equals(recipe.getUsername())) {
                recipes.add(recipe);
            }
        }
        return recipes.size() == 0 ? null : recipes;
    }

    public static void addAdmirer(String recipe_name, String admirer_name) {
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe_name.equals(recipe.getName())) {
                Recipe newRecipe = recipe.copyData();
                newRecipe.addAdmirer(admirer_name);
                recipeRepository.remove(recipe);
                recipeRepository.insert(newRecipe);
                break;
            }
        }
    }

    public static ArrayList<Recipe> populateSavedRecipesList(String username) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe.getAdmirers().contains(username)) {
                recipes.add(recipe);
            }
        }
        return recipes.size() == 0 ? null : recipes;
    }

}
