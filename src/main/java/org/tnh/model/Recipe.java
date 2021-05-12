package org.tnh.model;

public class Recipe {

    private String name;
    private String calories;
    private String time;
    private String instructions;

    public Recipe(String name, String calories, String time, String instructions) {
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
    }

    public Recipe() { }

    public String toString() {
        return name + "\n\nNumber of calories: " + calories + "\n\nTime: " + time + " minutes\n\n" + instructions;
    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getCalories() {
        return calories;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
