package org.tnh.model;

import java.util.ArrayList;
import java.util.Objects;

public class Recipe {

    private String name;
    private String instructions;
    private String calories;
    private String time;


    public Recipe(String name, String calories, String time, String instructions) {
        this.name = name;
        this.calories = calories;
        this.time = time;
        this.instructions = instructions;
    }

    public Recipe()
    {

    }

    public String toString() {
        return name + "\n\nNumber of calories: " + calories + "\n\nTime: " + time + " minutes\n\n" + instructions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
