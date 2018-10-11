package team58.cs2340.donationtracker.Models;

/**
 * This class represents various location types
 */
public enum LocationType {
    DROPOFF("drop off"),
    STORE("store"),
    WAREHOUSE("warehouse");

    private String typeName;

    LocationType (String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
