package team58.cs2340.donationtracker.Models;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private static final LocationManager instance = new LocationManager();

    private List<Location> locations;

    private static final Location defaultAllLocation = new Location(-1, "All Locations", "", "",
                                                                 "", "", "", "", null, "", "");

    public static LocationManager getInstance() {
        return instance;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public Location getDefaultAllLocation() {
        return defaultAllLocation;
    }
}
