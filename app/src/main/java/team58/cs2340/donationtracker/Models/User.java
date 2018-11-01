package team58.cs2340.donationtracker.Models;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Location location;


    public User(String firstName, String lastName, String email, String password, Role role,
                Location location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.location = location;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this(firstName, lastName, email, password, role, null);
    }

    public User(String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password, Role.USER);
    }

    public User() {

    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean equals(Object other) {
        if (null == other) return false;
        if (this == other) return true;
        if (!(other instanceof User)) return false;
        User that = (User) other;
        return this.email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return (31 * 17 + this.email.hashCode());
    }

    public boolean validLogin(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
