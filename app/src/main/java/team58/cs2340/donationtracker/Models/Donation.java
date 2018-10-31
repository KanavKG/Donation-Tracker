package team58.cs2340.donationtracker.Models;
import java.io.Serializable;
import java.util.Date;

public class Donation implements Serializable{

    private Date timeStamp;
    private String name;
    private Location location;
    private double value;
    private String shortDescription;
    private String fullDescription;
    Category category;
    private String comment;
    //String picture;

    public Donation() { }

    public Donation(Date timeStamp, String name, Location location, double value,
                    String shortDescription, String fullDescription, Category category, String comment) {
        this.timeStamp = timeStamp;
        this.name = name;
        this.location = location;
        this.value = value;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.comment = comment;
    }

    public Donation(String name, Location location, double value, String shortDescription,
                    String fullDescription, Category category, String comment) {
        this(new Date(), name, location, value, shortDescription, fullDescription, category, comment);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public  String toString() {
        return this.name;
    }
}
