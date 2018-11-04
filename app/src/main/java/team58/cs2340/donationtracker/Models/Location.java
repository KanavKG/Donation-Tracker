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

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = LocationType.valueOf(type);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

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
