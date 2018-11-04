package team58.cs2340.donationtracker.Models;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private static final LocationManager instance = new LocationManager();

    private FireBaseDB db;
    private List<Location> locations;

    private static final Location defaultAllLocation = new Location(-1, "All Locations", -1, -1,
            "", "", "", "", null, "", "");

    private LocationManager() {
        this.db = FireBaseDB.getInstance();
        locations = new ArrayList<>();
        locations = db.loadLocations();
    }

    public static LocationManager getInstance() {
        return instance;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
        db.addLocation(location);
    }

    public Location getDefaultAllLocation() {
        return defaultAllLocation;
    }
}
