package org.tnh.exceptions;

public class UncompletedFieldsException extends Exception
{
    public UncompletedFieldsException()
    {
        super("All field have to be completed!");
    }
}
