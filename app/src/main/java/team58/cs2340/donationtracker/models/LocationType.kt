package team58.cs2340.donationtracker.models

/**
 * This class represents various location types
 */
enum class LocationType private constructor(private val typeName: String) {
    DROPOFF("Drop Off"),
    STORE("Store"),
    WAREHOUSE("Warehouse");


    companion object {

        /**
         * Function to return location type enum from string
         * @param text string to be converted
         * @return location type enum of string
         */
        fun fromString(text: String): LocationType? {
            for (l in LocationType.values()) {
                if (l.typeName.equals(text, ignoreCase = true)) {
                    return l
                }
            }
            return null
        }
    }
}
