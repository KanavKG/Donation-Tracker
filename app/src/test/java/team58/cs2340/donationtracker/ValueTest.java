package team58.cs2340.donationtracker;

import org.junit.Test;
import team58.cs2340.donationtracker.models.Donation;
import static junit.framework.TestCase.assertEquals;

/**
 * Test the getValue method inside Donation
 */
public class ValueTest {

    /**
     * Tests when null value is passed in
     */
    @Test
    public void testNull() {
        assertEquals("Null string fails",0d, Donation.getValue(null));
    }

    /**
     * Test when empty string is passed in
     */
    @Test
    public void testEmptyString() {
        assertEquals("Empty string fails",0d, Donation.getValue(""));
    }

    /**
     * Test when a negative number is passed in
     */
    @Test
    public void testNegativeValue() {
        assertEquals("Negative value string fails",0d, Donation.getValue("-1"));
    }

    /**
     * Test when a valid number is passed in
     */
    @Test
    public void testValue() {
        assertEquals("Valid value string fails",1d, Donation.getValue("1"));
    }

}
