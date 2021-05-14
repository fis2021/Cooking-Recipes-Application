package org.tnh.model;

import org.dizitart.no2.objects.Id;

import java.util.ArrayList;
import java.util.UUID;

public class Recipe {
    @Id
    private UUID recipe_id;
    private String username;
    private String name;
    private String calories;
    private String time;
    private String instructions;
    private ArrayList<String> admirers = new ArrayList<>();

    public Recipe(String username, String name, String calories, String time, String instructions) {
        this.recipe_id = UUID.randomUUID();
        this.username = username;
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
    }

    public Recipe(UUID recipe_id, String username, String name, String calories, String time, String instructions) {
        this.recipe_id = recipe_id;
        this.username = username;
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
    }

    public Recipe() { }

    public Recipe copyData() {
        return new Recipe(recipe_id, username, name, calories, time, instructions);
    }

    public String toString() {
        return name + "\n\nNumber of calories: " + calories + "\n\nTime: " + time + " minutes\n\n" + instructions;
    }

    public UUID rand_UUID() {
        this.recipe_id = UUID.randomUUID();
        return this.recipe_id;
    }

    public UUID getRecipe_id() {
        return recipe_id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getAdmirers() {
        return admirers;
    }

    public void addAdmirer(String name) {
        admirers.add(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe r = (Recipe) o;

        return name.equals(r.name);
    }
}
