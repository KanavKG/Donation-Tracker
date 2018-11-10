package team58.cs2340.donationtracker.models;

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

    /**
     * Function to get first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Function to set first name
     * @param firstName to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Function to get last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Function to set last name
     * @param lastName to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Function to get role
     * @return role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Function to set role
     * @param role to be set
     */
    public void setRole(Role role) {
        this.role = role;
    }
    /**
     * Function to get location
     * @return location
     */
    public String getLocation() {
        return location;
    }
    /**
     * Function to set location
     * @param location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
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
