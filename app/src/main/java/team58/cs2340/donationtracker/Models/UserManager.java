package team58.cs2340.donationtracker.Models;

import android.util.Log;

import java.util.HashMap;

public class UserManager {
    private static final UserManager instance = new UserManager();

    private FireBaseDB db;
    private HashMap<String, User> users;
    private User currentUser;

    private UserManager() {
        this.db = FireBaseDB.getInstance();
        this.users = db.loadUsers();
        Log.d("FirebaseDB", "userManager getting users" + users.size());
    }

    public static UserManager getInstance() {
        return instance;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public int getUserNum() {
        return this.users.size();
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

    public boolean addUser(String email, User user) {
        Log.d("FirebseDB", "adding user UserManager");
        if (users.containsKey(email)) {
            return false;
        } else {
            users.put(user.getEmail(), user);
            db.addUser(user);
            return true;
        }
    }

    public boolean validLogin(String email, String password) {
        User user = users.get(email);
        if (user == null) {
            return false;
        } else if (user.checkPassword(password)) {
            setCurrentUser(user);
            return true;
        } else {
            return false;
        }
    }
}
