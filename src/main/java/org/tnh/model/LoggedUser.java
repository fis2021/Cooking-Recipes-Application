package org.tnh.model;

public class LoggedUser {

    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        LoggedUser.loggedUser = loggedUser;
    }

}
