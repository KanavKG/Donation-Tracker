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

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Location getLocation() {
        return location;
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
