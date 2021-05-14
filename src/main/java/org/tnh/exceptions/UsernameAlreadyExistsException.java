package org.tnh.exceptions;

public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException(String username) { super(String.format("An account with the username %s already exists!", username)); }

}
