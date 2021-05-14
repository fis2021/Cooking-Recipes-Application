package org.tnh.exceptions;

public class RecipeAlreadySavedException extends Exception {

    public RecipeAlreadySavedException() {
        super("You already saved this recipe!");
    }

}