package org.tnh.exceptions;

public class FirstNameIsNotUniqueException extends Exception {

    public FirstNameIsNotUniqueException()
    {
        super("First name is not unique!");
    }

}