package team58.cs2340.donationtracker.models;

/**
 * This class represents various location types
 */
public enum LocationType {
    DROPOFF("Drop Off"),
    STORE("Store"),
    WAREHOUSE("Warehouse");

    private final String typeName;

    LocationType (String typeName) {
        this.typeName = typeName;
    }

    /**
     * Function to return type name
     * @return type name to be returned
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * Function to return location type enum from string
     * @param text string to be converted
     * @return location type enum of string
     */
    public static LocationType fromString(String text) {
        for (LocationType l : LocationType.values()) {
            if (l.typeName.equalsIgnoreCase(text)) {
                return l;
            }
        }
        return null;
    }
}
