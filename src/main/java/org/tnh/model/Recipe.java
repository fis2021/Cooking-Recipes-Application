package org.tnh.model;

public class Recipe {

    private String username;
    private String name;
    private String calories;
    private String time;
    private String instructions;

    public Recipe(String username, String name, String calories, String time, String instructions) {
        this.username = username;
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
    }

    public Recipe() { }

    public String toString() {
        return name + "\n\nNumber of calories: " + calories + "\n\nTime: " + time + " minutes\n\n" + instructions;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
