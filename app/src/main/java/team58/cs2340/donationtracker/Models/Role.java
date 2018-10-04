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

    public String getRoleName() {
        return this.roleName;
    }
}
