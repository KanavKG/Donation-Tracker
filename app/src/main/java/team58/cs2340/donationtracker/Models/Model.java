package team58.cs2340.donationtracker.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private static final Model ourInstance = new Model();

    private HashMap<String, User> users;
    private User currentUser;
    private ArrayList<Location> locations;
    private ArrayList<Donation> donations;

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
        this.users = new HashMap<>(5);
        this.locations = new ArrayList<>();
        this.donations = new ArrayList<>();
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void clearCurrentUser() {
        this.currentUser = null;
    }

    public boolean addUser(String email, User user) {
        return users.put(user.getEmail(), user) != null;
    }

    public boolean validLogin(String email, String password) {
        User user = users.get(email);
        if (user == null) {
            return false;
        } else if (user.getPassword().equals(password)) {
            this.currentUser = user;
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public ArrayList<Donation> getDonations() {
        return donations;
    }

    public void addDonation(Donation donation) {
        donations.add(donation);
    }
}
