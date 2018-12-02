package team58.cs2340.donationtracker.models

/**
 * Current user local class
 */
class CurrUserLocal {

    /**
     * Function to return currentuser
     * @return currentUser
     */
    /**
     * Function to set current user
     * @param currentUser current user
     */
    var currentUser: User? = null

    /**
     * Function to get current user's location
     * @return currentUser location if applicable
     */
    val currentUserLocation: String?
        get() = if (this.currentUser != null) this.currentUser!!.location else null

    /**
     * Function to get current user's role
     * @return currentUser role if applicable
     */
    val currentUserRole: Role?
        get() = if (this.currentUser != null) this.currentUser!!.role else null

    /**
     * Function to clear current user
     */
    fun clearCurrentUser() {
        this.currentUser = null
    }

    companion object {
        /**
         * Function to return curruserlocal
         * @return instance of curruser
         */
        val instance = CurrUserLocal()
    }
}
