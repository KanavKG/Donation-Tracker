package team58.cs2340.donationtracker.Models;
import java.util.Date;

public class Donation {

    private Date timeStamp;
    Location location;
    String name;
    String shortDescription;
    String fullDescription;
    Double value;
    //String category;
    String comment;
    //String picture;

    public Donation(Date timeStamp, Location location, String name, String shortDescription,
                    String fullDescription, Double value, String comment) {
        this.timeStamp = timeStamp;
        this.location = location;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.value = value;
        this.comment = comment;
    }

    public Donation(Location location, String name, String shortDescription, String fullDescription,
                    Double value, String comment) {
        this(new Date(), location, name, shortDescription, fullDescription, value, comment);
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

    public Double getValue() {
        return value;
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

    public void setComment(String comment) {
        this.comment = comment;
    }
}
