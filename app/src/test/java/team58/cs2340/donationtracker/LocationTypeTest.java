package team58.cs2340.donationtracker;

import org.junit.Test;

import team58.cs2340.donationtracker.models.LocationType;
import team58.cs2340.donationtracker.models.Role;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;


/**
 * Tester class to check locationtype.fromString method
 * @author Kanav Garg
 */
public class LocationTypeTest {

    /**
     * Tests null input to fromString
     */
    @Test
    public void testNull() {
        assertNull("Null string fails", LocationType.fromString(null));
    }

    /**
     * Tests invalid input to fromString
     */
    @Test
    public void testInvalid() {
        assertNull("Invalid string fails", LocationType.fromString("nowhere"));
    }

    /**
     * Tests dropoff input to fromString
     */
    @Test
    public void testDropOff() {
        assertEquals("Correct output for Drop off", LocationType.DROPOFF, LocationType.fromString("Drop Off"));
    }

    /**
     * Tests Store input to fromString
     */
    @Test
    public void testStore() {
        assertEquals("Correct output for Store", LocationType.STORE,
                LocationType.fromString("Store"));
    }

    /**
     * Tests Warehouse input to fromString
     */
    @Test
    public void testWarehouse() {
        assertEquals("Correct output for Warehouse", LocationType.WAREHOUSE, LocationType.fromString("Warehouse"));
    }
}