package org.tnh.exceptions;

public class RecipeAlreadyRatedException extends Exception {

    public RecipeAlreadyRatedException() {
        super("You already rated this recipe!");
    }

}