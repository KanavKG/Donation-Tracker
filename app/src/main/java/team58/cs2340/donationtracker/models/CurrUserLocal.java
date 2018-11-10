package team58.cs2340.donationtracker.models;

public class CurrUserLocal {
    private static final CurrUserLocal instance = new CurrUserLocal();

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
