package team58.cs2340.donationtracker.Models;

import java.util.HashMap;

public class UserManager {
    private static final UserManager instance = new UserManager();

    private HashMap<String, User> users;
    private User currentUser;

    private UserManager() {
        this.users = new HashMap<>(5);
    }

    public static UserManager getInstance() {
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean addUser(String email, User user) {
        return users.put(user.getEmail(), user) != null;
    }

    public boolean validLogin(String email, String password) {
        User user = users.get(email);
        if (user == null) {
            return false;
        } else if (user.checkPassword(password)) {
            this.currentUser = user;
            return true;
        } else {
            return false;
        }
    }
}
