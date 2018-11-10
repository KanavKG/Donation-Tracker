package team58.cs2340.donationtracker.models;

import android.support.annotation.Nullable;

public class CurrUserLocal {
    private static final CurrUserLocal instance = new CurrUserLocal();

    @Nullable
    private User currentUser;

    /**
     * Function to return curruserlocal
     * @return instance of curruser
     */
    public static CurrUserLocal getInstance() {
        return instance;
    }

    /**
     * Function to return currentuser
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Function to set current user
     * @param currentUser current user
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Function to clear current user
     */
    public void clearCurrentUser() {
        this.currentUser = null;
    }
}
