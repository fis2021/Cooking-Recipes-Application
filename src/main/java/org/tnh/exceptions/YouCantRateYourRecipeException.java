package org.tnh.exceptions;

public class YouCantRateYourRecipeException extends Exception {

    public YouCantRateYourRecipeException()
    {
        super("You can't rate your own recipe!");
    }

}