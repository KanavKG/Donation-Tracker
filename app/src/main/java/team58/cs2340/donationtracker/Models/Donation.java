package team58.cs2340.donationtracker.Models;
import android.graphics.Bitmap;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Donation implements Serializable{

    @ServerTimestamp private Date timeStamp;
    private String name;
    private String location;
    private double value;
    private String shortDescription;
    private String fullDescription;
    private Category category;
    private String comment;
    private Bitmap photo;

    public Donation() { }

    public Donation( Date timeStamp, String name, String location, double value,
                    String shortDescription, String fullDescription, Category category, String comment) {
        this.timeStamp = timeStamp;
        this.name = name;
        this.location = location;
        this.value = value;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.comment = comment;
        //this.photo = photo;
    }

    public Donation(String name, String location, double value, String shortDescription,
                    String fullDescription, Category category, String comment) {
        this(new Date(), name, location, value, shortDescription, fullDescription, category, comment);
    }

    public boolean checkCategory(Category category) {
        return this.category.equals(category);
    }

    public boolean checkLocation(Location location) {
        return this.location.equals(location);
    }

    public boolean checkName(String name) {
        return this.name.equals(name);
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public  String toString() {
        return this.name;
    }
}

