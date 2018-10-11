package team58.cs2340.donationtracker.Models;

public class Location {

    private String name;
    private LocationType type;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;

    public Location(String name, LocationType type, double longitude, double latitude,
                    String address, String phoneNumber) {
        this.name = name;
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
