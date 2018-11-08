package team58.cs2340.donationtracker.Models;

public class CurrUserLocal {
    private static final CurrUserLocal instance = new CurrUserLocal();

    private User currentUser;

    public static CurrUserLocal getInstance() {
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
