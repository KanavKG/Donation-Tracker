package team58.cs2340.donationtracker;

import org.junit.Test;
import team58.cs2340.donationtracker.models.Role;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class RoleTest {

    @Test
    public void testNull() {
        assertNull("Null string fails", Role.fromString(null));
    }

    @Test
    public void testInvalid() {
        assertNull("Invalid string fails", Role.fromString("cabbage"));
    }

    @Test
    public void testUser() {
        assertEquals("Correct output for User", Role.USER, Role.fromString("User"));
    }

    @Test
    public void testLocationEmployee() {
        assertEquals("Correct output for Location Employee", Role.LOCATIONEMPLOYEE,
                Role.fromString("Location Employee"));
    }

    @Test
    public void testAdmin() {
        assertEquals("Correct output for Admin", Role.ADMIN, Role.fromString("Admin"));
    }

}