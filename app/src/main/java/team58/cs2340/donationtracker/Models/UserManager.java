package team58.cs2340.donationtracker.Models;

import java.util.HashMap;

public class UserManager {
    private static final UserManager instance = new UserManager();

    private User currentUser;

    public static UserManager getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void clearCurrentUser() {
        this.currentUser = null;
    }
}
