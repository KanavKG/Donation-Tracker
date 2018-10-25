package team58.cs2340.donationtracker.Models;
import java.util.Date;

public class Donation {

    private Date timeStamp;
    private Location location;
    private String name;
    private double value;
    private String shortDescription;
    private String fullDescription;
    Category category;
    private String comment;
    //String picture;

    public Donation(Date timeStamp, String name, Location location, double value, String shortDescription,
                    String fullDescription, Category category, String comment) {
        this.timeStamp = timeStamp;
        this.name = name;
        this.location = location;
        this.value = value;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.comment = comment;
    }

    public Donation(String name, Location location, double value, String shortDescription,
                    String fullDescription, Category category, String comment) {
        this(new Date(), name, location, value, shortDescription, fullDescription, category, comment);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public double getValue() {
        return value;
    }

    public Category getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
