package team58.cs2340.donationtracker.Models;

/**
 * This class represents various user roles
 */
public enum Role {
    USER("User"),
    LOCATIONEMPLOYEE("Location Employee"),
    ADMIN("Admin");

    private String roleName;

    Role (String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

    public static Role fromString(String text) {
        for (Role r : Role.values()) {
            if (r.roleName.equalsIgnoreCase(text)) {
                return r;
            }
        }
        return null;
    }
}
