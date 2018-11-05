package team58.cs2340.donationtracker.Models;

public enum Category {
    APPLIANCES("Appliances"),
    BABY("Baby"),
    BAGSANDACCESSORIES("Bags and Accessories"),
    BOOKSMOVIESMUSICGAMES("Books, Movies, Music and Games"),
    CLOTHING("Clothing"),
    ELECTRONICS("Electronics"),
    FURNITURE("Furniture"),
    SHOES("Shoes"),
    SPORTSANDOUTDOORS("Sports and Outdoors"),
    TOYS("Toys");



    private String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }

    public static Category fromString(String text) {
        for (Category c : Category.values()) {
            if (c.categoryName.equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}
