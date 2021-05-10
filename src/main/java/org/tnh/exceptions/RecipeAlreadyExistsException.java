package org.tnh.exceptions;

public class RecipeAlreadyExistsException extends Exception {

    private String name;

    public RecipeAlreadyExistsException(String name) {
        super(String.format("A recipe with the name %s already exists!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
