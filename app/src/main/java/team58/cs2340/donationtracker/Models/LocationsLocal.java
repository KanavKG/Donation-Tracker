package team58.cs2340.donationtracker.Models;

import java.util.ArrayList;
import java.util.List;

public class LocationsLocal {

    private static final LocationsLocal instance = new LocationsLocal();

    private List<Location> locations;

    private static final Location defaultAllLocation = new Location(-1, "All Locations", "", "",
            "", "", "", "", null, "", "");

    public LocationsLocal() {
        this.locations = new ArrayList<>();
    }

    public static LocationsLocal getInstance() {
        return instance;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public static Location getDefaultAllLocation() {
        return defaultAllLocation;
    }
}
