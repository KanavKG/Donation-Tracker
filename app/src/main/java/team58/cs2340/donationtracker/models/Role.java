package team58.cs2340.donationtracker.models;

/**
 * This class represents various user roles
 */
public enum Role {
    USER("User"),
    LOCATIONEMPLOYEE("Location Employee"),
    ADMIN("Admin");

    private final String roleName;

    Role (String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

    /**
     * Function to convert string to role enum
     * @param text to be converted
     * @return enum of the text
     */
    public static Role fromString(String text) {
        for (Role r : Role.values()) {
            if (r.roleName.equalsIgnoreCase(text)) {
                return r;
            }
        }
        return null;
    }
}
