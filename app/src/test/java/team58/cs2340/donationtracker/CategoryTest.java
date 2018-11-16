package team58.cs2340.donationtracker;

import org.junit.Test;
import team58.cs2340.donationtracker.models.Category;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

/**
 * Class for category tests
 */
public class CategoryTest {

    /**
     * Tests null input to fromString
     */
    @Test
    public void testNull() {
        assertNull("Null string fails", Category.fromString(null));
    }

    /**
     * Tests invalid input to fromString
     */
    @Test
    public void testInvalid() {
        assertNull("Invalid string fails", Category.fromString("potato"));
    }

    /**
     * Tests Appliances input to fromString
     */
    @Test
    public void testAppliances() {
        assertEquals("Correct output for Appliances",
                Category.APPLIANCES, Category.fromString("Appliances"));
    }

    /**
     * Tests Baby input to fromString
     */
    @Test
    public void testBaby() {
        assertEquals("Correct output for Baby",Category.BABY, Category.fromString("Baby"));
    }

    /**
     * Tests Bags and Accessories input to fromString
     */
    @Test
    public void testBagsAndAccessories() {
        assertEquals("Correct output for Bags and Accessories",
                Category.BAGSANDACCESSORIES, Category.fromString("Bags and Accessories"));
    }

    /**
     * Tests Books and Music input to fromString
     */
    @Test
    public void testBooksAndMusic() {
        assertEquals("Correct output for Books and Music",
                Category.BOOKSANDMUSIC, Category.fromString("Books and Music"));
    }

    /**
     * Tests Clothing input to fromString
     */
    @Test
    public void testClothing() {
        assertEquals("Correct output for Clothing",
                Category.CLOTHING, Category.fromString("Clothing"));
    }

    /**
     * Tests Electronics input to fromString
     */
    @Test
    public void testElectronics() {
        assertEquals("Empty string fails",Category.ELECTRONICS, Category.fromString("Electronics"));
    }

    /**
     * Tests Food input to fromString
     */
    @Test
    public void testFood() {
        assertEquals("Correct output for Food",Category.FOOD, Category.fromString("Food"));
    }

    /**
     * Tests Furniture input to fromString
     */
    @Test
    public void testFurniture() {
        assertEquals("Correct output for Furniture",
                Category.FURNITURE, Category.fromString("Furniture"));
    }

    /**
     * Tests Movies and Games input to fromString
     */
    @Test
    public void testMoviesAndGames() {
        assertEquals("Correct output for Movies and Games",
                Category.MOVIESANDGAMES, Category.fromString("Movies and Games"));
    }

    /**
     * Tests Sports and Outdoors input to fromString
     */
    @Test
    public void testSportsAndOutdoors() {
        assertEquals("Correct output for Sports and Outdoors",
                Category.SPORTSANDOUTDOORS, Category.fromString("Sports and Outdoors"));
    }

    /**
     * Tests Toys input to fromString
     */
    @Test
    public void testToys() {
        assertEquals("Correct output for Toys",
                Category.TOYS, Category.fromString("Toys"));
    }

}