package team58.cs2340.donationtracker.Models;

public class User {

    private String firstName;
    private String lastName;
    private Role role;
    private String location;


    public User(String firstName, String lastName, Role role,
                String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.location = location;
    }

    public User(String firstName, String lastName, Role role) {
        this(firstName, lastName, role, null);
    }

    public User(String firstName, String lastName) {
        this(firstName, lastName, Role.USER);
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean equals(Object other) {
        if (null == other) return false;
        if (this == other) return true;
        if (!(other instanceof User)) return false;
        User that = (User) other;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return (31 * 17 + this.lastName.hashCode() + this.firstName.hashCode());
    }
}
