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
        assertEquals("Invalid string fails",null, Category.fromString("potato"));
    }

    @Test
    public void testAppliances() {
        assertEquals("Correct output for Appliances",Category.APPLIANCES, Category.fromString("Appliances"));
    }

    @Test
    public void testBaby() {
        assertEquals("Correct output for Baby",Category.BABY, Category.fromString("Baby"));
    }

    @Test
    public void testBagsAndAccessories() {
        assertEquals("Correct output for Bags and Accessories",Category.BAGSANDACCESSORIES, Category.fromString("Bags and Accessories"));
    }

    @Test
    public void testBooksAndMusic() {
        assertEquals("Correct output for Books and Music",Category.BOOKSANDMUSIC, Category.fromString("Books and Music"));
    }

    @Test
    public void testClothing() {
        assertEquals("Correct output for Clothing",Category.CLOTHING, Category.fromString("Clothing"));
    }

    @Test
    public void testElectronics() {
        assertEquals("Empty string fails",Category.ELECTRONICS, Category.fromString("Electronics"));
    }

    @Test
    public void testFood() {
        assertEquals("Correct output for Food",Category.FOOD, Category.fromString("Food"));
    }

    @Test
    public void testFurniture() {
        assertEquals("Correct output for Furniture",Category.FURNITURE, Category.fromString("Furniture"));
    }

    @Test
    public void testMoviesAndGames() {
        assertEquals("Correct output for Movies and Games",Category.MOVIESANDGAMES, Category.fromString("Movies and Games"));
    }

    @Test
    public void testSportsAndOutdoors() {
        assertEquals("Correct output for Sports and Outdoors",Category.SPORTSANDOUTDOORS, Category.fromString("Sports and Outdoors"));
    }

    @Test
    public void testToys() {
        assertEquals("Correct output for Toys",Category.TOYS, Category.fromString("Toys"));
    }

}