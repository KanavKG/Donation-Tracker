package team58.cs2340.donationtracker.Models;

/**
 * This class represents various location types
 */
public enum LocationType {
    DROPOFF("Drop Off"),
    STORE("Store"),
    WAREHOUSE("Warehouse");

    private String typeName;

    LocationType (String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public static LocationType fromString(String text) {
        for (LocationType l : LocationType.values()) {
            if (l.typeName.equalsIgnoreCase(text)) {
                return l;
            }
        }
        return null;
    }
}
