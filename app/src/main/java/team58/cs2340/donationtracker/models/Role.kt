package team58.cs2340.donationtracker.models

/**
 * This class represents various user roles
 */
enum class Role private constructor(private val roleName: String) {
    USER("User"),
    LOCATIONEMPLOYEE("Location Employee"),
    ADMIN("Admin");

    override fun toString(): String {
        return this.roleName
    }

    companion object {

        /**
         * Function to convert string to role enum
         * @param text to be converted
         * @return enum of the text
         */
        fun fromString(text: String): Role? {
            for (r in Role.values()) {
                if (r.roleName.equals(text, ignoreCase = true)) {
                    return r
                }
            }
            return null
        }
    }
}
