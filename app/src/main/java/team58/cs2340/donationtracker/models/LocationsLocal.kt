package team58.cs2340.donationtracker.models

import java.util.ArrayList

/**
 * final class for locations
 */
class LocationsLocal private constructor() {

    private val locations: MutableList<Location>

    init {
        locations = ArrayList()
    }

    /**
     * Function to get locations
     * @return locations to be returned
     */
    fun getLocations(): List<Location> {
        return locations
    }

    /**
     * Function to add location
     * @param location location to be added
     */
    fun addLocation(location: Location) {
        locations.add(location)
    }

    companion object {

        /**
         * Function to get instance
         * @return instance to be returned
         */
        val instance = LocationsLocal()

        /**
         * Function to get all default locations
         * @return all default locations to be returned
         */
        val defaultAllLocation = Location(-1, "All Locations", "", "",
                "", "", "", "", null, "", "")
    }
}
