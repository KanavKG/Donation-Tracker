package team58.cs2340.donationtracker.Models;

import java.util.HashMap;

class Model {
    private static final Model ourInstance = new Model();

    HashMap<String, User> users;
    User currentUser;

    static Model getInstance() {
        return ourInstance;
    }

    private Model() {
        this.users = new HashMap<>(5);
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public boolean addUser(String email, User user) {
        return users.put(user.getEmail(), user) == null;
    }

    public boolean validLogin(String email, String password) {
        User user = users.get(email);
        if (user == null) {
            return false;
        } else {
            return user.getPassword().equals(password);
        }
    }
}
