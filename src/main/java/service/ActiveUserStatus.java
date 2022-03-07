package service;

import model.User;

public class ActiveUserStatus {
    private static final ActiveUserStatus instance = new ActiveUserStatus();
    private User loggedInUser;

    public static ActiveUserStatus getInstance() {
        return instance;
    }

    public boolean hasLoggedInUser() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void logIn(User user) {
        loggedInUser = user;
    }

    public void logOut() {
        loggedInUser = null;
    }
}
