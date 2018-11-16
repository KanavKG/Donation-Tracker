package team58.cs2340.donationtracker;

import org.junit.Test;
import team58.cs2340.donationtracker.models.Role;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;




public class LocationTypeTest {

    /**
     * Tests null input to fromString
     */
    @Test
    public void testNull() {
        assertNull("Null string fails", Role.fromString(null));
    }

    /**
     * Tests invalid input to fromString
     */
    @Test
    public void testInvalid() {
        assertNull("Invalid string fails", Role.fromString("cabbage"));
    }

    /**
     * Tests User input to fromString
     */
    @Test
    public void testUser() {
        assertEquals("Correct output for User", Role.USER, Role.fromString("User"));
    }

    /**
     * Tests Location Employee input to fromString
     */
    @Test
    public void testLocationEmployee() {
        assertEquals("Correct output for Location Employee", Role.LOCATIONEMPLOYEE,
                Role.fromString("Location Employee"));
    }

    /**
     * Tests Admin input to fromString
     */
    @Test
    public void testAdmin() {
        assertEquals("Correct output for Admin", Role.ADMIN, Role.fromString("Admin"));
    }
}

