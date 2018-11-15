package team58.cs2340.donationtracker;

import org.junit.Test;
import team58.cs2340.donationtracker.models.Donation;
import static junit.framework.TestCase.assertEquals;

public class ValueTest {

    @Test
    public void testNull() {
        assertEquals("Null string fails",0d, Donation.getValue(null));
    }

    @Test
    public void testEmptyString() {
        assertEquals("Empty string fails",0d, Donation.getValue(""));
    }

    @Test
    public void testNegativeValue() {
        assertEquals("Negative value string fails",0d, Donation.getValue("-1"));
    }

    @Test
    public void testValue() {
        assertEquals("Valid value string fails",1d, Donation.getValue("1"));
    }

}
