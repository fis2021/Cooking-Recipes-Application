package org.tnh.exceptions;

public class EmptyDataBaseException extends Exception {

    public EmptyDataBaseException() {
        super("There are no recipes, go create one!");
    }

}
