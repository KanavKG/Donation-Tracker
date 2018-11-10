package team58.cs2340.donationtracker.Models;

import java.util.ArrayList;
import java.util.List;

public class LocationsLocal {

    private static final LocationsLocal instance = new LocationsLocal();

    private List<Location> locations;

    private LocationsLocal() {
        locations = new ArrayList<>();
    }

    private static final Location defaultAllLocation = new Location(-1, "All Locations", "", "",
            "", "", "", "", null, "", "");

    /**
     * Function to get instance
     * @return instance to be returned
     */
    public static LocationsLocal getInstance() {
        return instance;
    }
    /**
     * Function to get locations
     * @return locations to be returned
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Function to add location
     * @param location location to be added
     */
    public void addLocation(Location location) {
        locations.add(location);
    }
    /**
     * Function to get all default locations
     * @return all default locations to be returned
     */
    public static Location getDefaultAllLocation() {
        return defaultAllLocation;
    }
}
