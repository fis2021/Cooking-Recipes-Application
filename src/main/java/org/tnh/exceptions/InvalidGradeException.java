package org.tnh.exceptions;

public class InvalidGradeException extends Exception {

    public InvalidGradeException() {
        super("You can only introduce grades from 1 to 10!");
    }

}