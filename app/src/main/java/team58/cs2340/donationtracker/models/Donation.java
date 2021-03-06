package team58.cs2340.donationtracker.models;
import android.graphics.Bitmap;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

/**
 * Donation class to create donation objects
 */
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

    /**
     * Empty constructor for donations
     */
    public Donation() { }

    /**
     * Constructor to create a donation object
     * @param timeStamp current time
     * @param name name of item
     * @param location location of item
     * @param value value of item
     * @param shortDescription of item
     * @param fullDescription of item
     * @param category of item
     * @param comment any comments
     */
    public Donation( Date timeStamp, String name, String location, double value,
                    String shortDescription, String fullDescription,
                     Category category, String comment) {
        this.timeStamp = (Date) timeStamp.clone();
        this.name = name;
        this.location = location;
        this.value = value;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.comment = comment;
        //this.photo = photo;
    }

    /**
     * Constructor to create a donation without date information
     * @param name name of item
     * @param location location of item
     * @param value value of item
     * @param shortDescription of item
     * @param fullDescription of item
     * @param category of item
     * @param comment any comments
     */
    public Donation(String name, String location, double value, String shortDescription,
                    String fullDescription, Category category, String comment) {
        this(new Date(), name, location, value, shortDescription,
                fullDescription, category, comment);
    }

    /**
     * Function to check location
     * @param location location to be checked
     * @return boolean value of check
     */
    public boolean checkLocation(String location) {
        return this.location.equals(location);
    }

    /**
     * Function to check location
     * @param name name to be checked
     * @return boolean value of check
     */
    public boolean checkName(String name) {
        return this.name.equals(name);
    }

    /**
     * Function to get time stamp
     * @return current time stamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Function to set time stamp
     * @param timeStamp current time stamp
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Function to get location
     * @return current location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Function to set location
     * @param location location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Function to get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Function to set name
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function to get short description
     * @return short description
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Function to set short description
     * @param shortDescription to be set
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Function to get full description
     * @return full description of item
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * Function to set full description
     * @param fullDescription to be set
     */
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    /**
     * Function to get value of item
     * @return value of the item
     */
    public double getValue() {
        return value;
    }

    /**
     * Function to set value
     * @param value value of item
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Function to get category of item
     * @return category of item
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Function to set category of item
     * @param category to be set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Function to get comment
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Function to set comment
     * @param comment comment to be set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Function to get photo
     * @return photo to be returned
     */
    public Bitmap getPhoto() {
        return photo;
    }

    /**
     * Function to set photo
     * @param photo photo to be set
     */
    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    @Override
    public  String toString() {
        return this.name;
    }

    /**
     * Function to get price of item as a double
     * @param v String that is to be converted to a double
     * @return Double value of item
     */
    public static double getValue(String v) {
        if ("".equals(v) || (v == null)) {
            return 0;
        }
        double value = Double.parseDouble(v);
        if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }
}

