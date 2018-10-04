package team58.cs2340.donationtracker.Models;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
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
