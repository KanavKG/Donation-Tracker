package team58.cs2340.donationtracker;

import org.junit.Test;
import team58.cs2340.donationtracker.models.Category;
import static junit.framework.TestCase.assertEquals;

public class CategoryTest {

    @Test
    public void testNull() {
        assertEquals("Null string fails",null, Category.fromString(null));
    }

    @Test
    public void testInvalid() {
        assertEquals("Empty string fails",null, Category.fromString("potato"));
    }

    @Test
    public void testAppliances() {
        assertEquals("Empty string fails",Category.APPLIANCES, Category.fromString("Appliances"));
    }

    @Test
    public void testBaby() {
        assertEquals("Empty string fails",Category.BABY, Category.fromString("Baby"));
    }

    @Test
    public void testBagsAndAccessories() {
        assertEquals("Empty string fails",Category.BAGSANDACCESSORIES, Category.fromString("Bags and Accessories"));
    }

    @Test
    public void testBooksAndMusic() {
        assertEquals("Empty string fails",Category.BOOKSANDMUSIC, Category.fromString("Books and Music"));
    }

    @Test
    public void testClothing() {
        assertEquals("Empty string fails",Category.CLOTHING, Category.fromString("Clothing"));
    }

    @Test
    public void testElectronics() {
        assertEquals("Empty string fails",Category.ELECTRONICS, Category.fromString("Electronics"));
    }

    @Test
    public void testFood() {
        assertEquals("Empty string fails",Category.FOOD, Category.fromString("Food"));
    }

    @Test
    public void testFurniture() {
        assertEquals("Empty string fails",Category.FURNITURE, Category.fromString("Furniture"));
    }

    @Test
    public void testMoviesAndGames() {
        assertEquals("Empty string fails",Category.MOVIESANDGAMES, Category.fromString("Movies and Games"));
    }

    @Test
    public void testSportsAndOutdoors() {
        assertEquals("Empty string fails",Category.SPORTSANDOUTDOORS, Category.fromString("Sports and Outdoors"));
    }

    @Test
    public void testToys() {
        assertEquals("Empty string fails",Category.TOYS, Category.fromString("Toys"));
    }

}