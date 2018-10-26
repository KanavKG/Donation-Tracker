package team58.cs2340.donationtracker.Models;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class Donation implements Serializable{

    private Date timeStamp;
    private Location location;
    private String name;
    private double value;
    private String shortDescription;
    private String fullDescription;
    //String category;
    private String comment;
    private Bitmap photo;

    public Donation() { }

    public Donation( Date timeStamp, String name, Location location, double value,
                    String shortDescription, String fullDescription, String comment, Bitmap photo) {
        this.timeStamp = timeStamp;
        this.location = location;
        this.name = name;
        this.value = value;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.comment = comment;
        this.photo = photo;
    }

    public Donation(String name, Location location, double value, String shortDescription,
                    String fullDescription, String comment, Bitmap photo) {
        this(new Date(), name, location, value, shortDescription, fullDescription, comment, photo);
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

    public String getComment() {
        return comment;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public  String toString() {
        return this.name;
    }
}

