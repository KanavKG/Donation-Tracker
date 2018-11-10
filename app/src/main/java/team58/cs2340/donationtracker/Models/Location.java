package team58.cs2340.donationtracker.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Location implements Serializable{

    private int key;
    private String name;
    private String latitude;
    private String longitude;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private LocationType type;
    private String phoneNumber;
    private String website;

    public Location() { }
    public Location(int key, String name, String latitude, String longitude,
                    String streetAddress, String city, String state,
                    String zip, LocationType type, String phoneNumber,
                    String website) {
        this.key = key;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    /**
     * Function to get key
     * @return key to be returned
     */
    public int getKey() {
        return key;
    }

    /**
     * Function to set key
     * @param key to be set
     */
    public void setKey(int key) {
        this.key = key;
    }
    /**
     * Function to get name
     * @return name to be returned
     */
    public String getName() {
        return name;
    }
    /**
     * Function to set name
     * @param name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Function to get latitude
     * @return latitude to be returned
     */
    public String getLatitude() {
        return latitude;
    }
    /**
     * Function to set latitude
     * @param latitude to be set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    /**
     * Function to get longitude
     * @return longitude to be returned
     */
    public String getLongitude() {
        return longitude;
    }
    /**
     * Function to set longitude
     * @param longitude to be set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    /**
     * Function to get street address
     * @return street address to be returned
     */
    public String getStreetAddress() {
        return streetAddress;
    }
    /**
     * Function to set street address
     * @param streetAddress to be set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    /**
     * Function to get city
     * @return city to be returned
     */
    public String getCity() {
        return city;
    }
    /**
     * Function to set city
     * @param city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Function to get state
     * @return state to be returned
     */
    public String getState() {
        return state;
    }
    /**
     * Function to set state
     * @param state to be set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * Function to get zip
     * @return zip to be returned
     */
    public String getZip() {
        return zip;
    }
    /**
     * Function to set zip
     * @param zip to be set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }
    /**
     * Function to get type
     * @return type to be returned
     */
    public LocationType getType() {
        return type;
    }
    /**
     * Function to set type
     * @param type to be set
     */
    public void setType(String type) {
        this.type = LocationType.valueOf(type);
    }
    /**
     * Function to get phone number
     * @return phone number to be returned
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Function to set phone number
     * @param phoneNumber to be set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Function to get website
     * @return website to be returned
     */
    public String getWebsite() {
        return website;
    }
    /**
     * Function to set website
     * @param website to be set
     */
    public void setWebsite(String website) {
        this.website = website;
    }


    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location that = (Location) obj;
        return this.name.equals(that.name);
    }
}
