package org.tnh.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.tnh.exceptions.*;
import org.tnh.model.Recipe;
import java.util.ArrayList;
import java.util.UUID;
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
        if (!pattern.matcher(name).find()
            || !pattern.matcher(calories).find()
            || !pattern.matcher(time).find()
            || !pattern.matcher(instructions).find())
                throw new UncompletedFieldsException();
    }

    public static void uncompletedNameField(String name) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        if (!pattern.matcher(name).find()) throw new UncompletedFieldsException();
    }

    public static void couldNotFindRecipe(String name) throws CouldNotFindRecipeException
    {
        boolean ok = false;
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe.getName().contains(name)) {
                ok = true;
                break;
            }
        }
        if (!ok) throw new CouldNotFindRecipeException();
    }

    public static void couldNotFindThisRecipe(String name) throws CouldNotFindRecipeException
    {
        boolean ok = false;
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe.getName().contains(name)) {
                ok = true;
                break;
            }
        }
        if (!ok) throw new CouldNotFindRecipeException();
    }

    public static void emptyDataBase() throws EmptyDataBaseException
    {
        if (recipeRepository.size() == 0) throw new EmptyDataBaseException();
    }

    public static void emptySavedDataBase(String username) throws EmptyDataBaseException
    {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe.getAdmirers().contains(username)) {
                recipes.add(recipe);
            }
        }
        if (recipes.isEmpty()) throw new EmptyDataBaseException();
    }

    public static void emptyCreatedRecipesDataBase(String username) throws EmptyDataBaseException
    {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            if (username.equals(recipe.getUsername())) {
                recipes.add(recipe);
            }
        }
        if (recipes.isEmpty()) throw new EmptyDataBaseException();
    }

    public static void emptyRatingFields(String name, String grade) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        if (!pattern.matcher(name).find()
                || !pattern.matcher(grade).find())
            throw new UncompletedFieldsException();
    }

    public static void invalidGrade(String grade) throws InvalidGradeException
    {
        int integer = Integer.parseInt(grade);
        if(integer < 1 || integer > 10)
            throw new InvalidGradeException();
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

    public static ArrayList<Recipe> populateSavedRecipesList(String username) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe.getAdmirers().contains(username)) {
                recipes.add(recipe);
            }
        }
        return recipes.size() == 0 ? null : recipes;
    }

    public static void addAdmirer(String recipe_name, String admirer_name) throws RecipeAlreadySavedException{
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

    public static void addRating(String recipe_name, String score, String admirer_name, String username) throws RecipeAlreadyRatedException{
        for (Recipe recipe : recipeRepository.find()) {
            if (recipe_name.equals(recipe.getName()) && !(username.equals(recipe.getUsername()))) {
                Recipe newRecipe = recipe.copyData();
                newRecipe.addRating(admirer_name, Integer.parseInt(score));
                recipeRepository.remove(recipe);
                recipeRepository.insert(newRecipe);
                break;
            }
        }
    }

    public static void deleteRecipe(String searched_recipe) {
        for (Recipe recipe : recipeRepository.find()) {
            if (searched_recipe.equals(recipe.getName())) {
                recipeRepository.remove(recipe);
                break;
            }
        }
    }

    public static void modifyRecipe(String name, String calories, String time, String instructions, String searched_recipe) {
        for (Recipe recipe : recipeRepository.find()) {
            if(searched_recipe.equals(recipe.getName()))
            {
                Recipe newRecipe = recipe.copyData();
                if(name.equals(""))
                    newRecipe.setName(newRecipe.getName());
                else
                    newRecipe.setName(name);
                if(calories.equals(""))
                    newRecipe.setCalories(newRecipe.getCalories());
                else
                    newRecipe.setCalories(calories);
                if(time.equals(""))
                    newRecipe.setTime(newRecipe.getTime());
                else
                    newRecipe.setTime(time);
                if(instructions.equals(""))
                    newRecipe.setInstructions(newRecipe.getInstructions());
                else
                    newRecipe.setInstructions(instructions);
                newRecipe.setRating("not yet rated");
                recipeRepository.remove(recipe);
                recipeRepository.insert(newRecipe);
                break;
            }
        }
    }

    public static boolean recipeFound(String name, String username)
    {
        for(Recipe recipe : recipeRepository.find())
        {
            if(name.equals(recipe.getName()) && username.equals(recipe.getUsername()))
                return true;
        }
        return false;
    }

}
