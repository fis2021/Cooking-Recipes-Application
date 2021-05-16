package org.tnh.exceptions;

public class YouCantModifyThisRecipe extends Exception {

    public YouCantModifyThisRecipe()
    {
        super("You can't modify recipes that you don't own!");
    }

}