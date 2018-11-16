package team58.cs2340.donationtracker.models;

import android.support.annotation.Nullable;

/**
 * Current user local class
 */
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
    @Nullable
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Function to set current user
     * @param currentUser current user
     */
    public void setCurrentUser(@Nullable User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Function to clear current user
     */
    public void clearCurrentUser() {
        this.currentUser = null;
    }

    /**
     * Function to get current user's location
     * @return currentUser location if applicable
     */
    public String getCurrentUserLocation() {
        return (this.currentUser != null) ? this.currentUser.getLocation() : null;
    }

    /**
     * Function to get current user's role
     * @return currentUser role if applicable
     */
    public Role getCurrentUserRole() {
        return (this.currentUser != null) ? this.currentUser.getRole() : null;
    }
}
