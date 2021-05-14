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
    private String rating = "not yet rated";
    private int score = 0;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> raters = new ArrayList<>();
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> admirers = new ArrayList<>();

    public Recipe(String username, String name, String calories, String time, String instructions) {
        this.recipe_id = UUID.randomUUID();
        this.username = username;
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
    }

    public Recipe(UUID recipe_id, String username, String name, String calories, String time, String instructions,
                  String rating, int total_rating, ArrayList<String> raters, ArrayList<String> admirers) {

        this.recipe_id = recipe_id;
        this.username = username;
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
        this.rating = rating;
        this.score = total_rating;
        this.raters = raters;
        this.admirers = admirers;
    }

    public Recipe() { }

    public Recipe copyData() {
        return new Recipe(recipe_id, username, name, calories, time, instructions, rating, score, raters, admirers);
    }

    public String toString() {
        return name + "\n\nNumber of calories: " + calories + "\n\nTime: " + time + " minutes\n\n" + instructions;
    }

    public UUID rand_UUID() {
        this.recipe_id = UUID.randomUUID();
        return this.recipe_id;
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

    public void addAdmirer(String name) {
        if (admirers.contains(name)) {
            System.out.println("You already saved this recipe!");
        } else {
            admirers.add(name);
        }
    }

    public void addRating(String rater, int score) {
        if (raters.contains(rater)) {
            System.out.println("You already rated this recipe!");
        } else {
            raters.add(rater);
            this.score += score;
            rating = String.valueOf((float) this.score / raters.size());
        }
    }

    public String getRating() {
        return rating;
    }

    public UUID getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(UUID recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<String> getRaters() {
        return raters;
    }

    public void setRaters(ArrayList<String> raters) {
        this.raters = raters;
    }

    public ArrayList<String> getAdmirers() {
        return admirers;
    }

    public void setAdmirers(ArrayList<String> admirers) {
        this.admirers = admirers;
    }
}
