package team58.cs2340.donationtracker.models

/**
 * User class containing attributes of a user
 */
class User {

    /**
     * Function to get first name
     * @return first name
     */
    /**
     * Function to set first name
     * @param firstName to be set
     */
    var firstName: String? = null
    /**
     * Function to get last name
     * @return last name
     */
    /**
     * Function to set last name
     * @param lastName to be set
     */
    var lastName: String? = null
    /**
     * Function to get role
     * @return role
     */
    /**
     * Function to set role
     * @param role to be set
     */
    var role: Role? = null
    /**
     * Function to get location
     * @return location
     */
    /**
     * Function to set location
     * @param location to be set
     */
    var location: String? = null

    /**
     * Constructor to instantiate a user object
     * @param firstName First name of the user
     * @param lastName Last name of the user
     * @param role Role of the user
     * @param location Location Branch of the user
     */
    constructor(firstName: String, lastName: String, role: Role,
                location: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.role = role
        this.location = location
    }

    private constructor(firstName: String, lastName: String, role: Role) : this(firstName, lastName, role, null) {}

    /**
     * Empty user constructor for login activity
     */
    constructor() {}

    override fun equals(other: Any?): Boolean {
        if (null == other) {
            return false
        }
        if (this === other) {
            return true
        }
        if (other !is User) {
            return false
        }
        val that = other as User?
        return this.hashCode() == that!!.hashCode()
    }

    override fun hashCode(): Int {
        return 31 * 17 + this.lastName!!.hashCode() + this.firstName!!.hashCode()
    }
}
