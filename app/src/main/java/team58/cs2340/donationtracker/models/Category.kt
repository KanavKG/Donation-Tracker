package team58.cs2340.donationtracker.models

/**
 * Category enum
 */
enum class Category private constructor(private val categoryName: String) {
    APPLIANCES("Appliances"),
    BABY("Baby"),
    BAGSANDACCESSORIES("Bags and Accessories"),
    BOOKSANDMUSIC("Books and Music"),
    CLOTHING("Clothing"),
    ELECTRONICS("Electronics"),
    FOOD("Food"),
    FURNITURE("Furniture"),
    MOVIESANDGAMES("Movies and Games"),
    SPORTSANDOUTDOORS("Sports and Outdoors"),
    TOYS("Toys");

    override fun toString(): String {
        return this.categoryName
    }

    companion object {

        /**
         * Function to convert string to enum
         * @param text string to be converted
         * @return enum of string
         */
        fun fromString(text: String): Category? {
            for (c in Category.values()) {
                if (c.categoryName.equals(text, ignoreCase = true)) {
                    return c
                }
            }
            return null
        }
    }
}
