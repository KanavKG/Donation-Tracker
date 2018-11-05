package team58.cs2340.donationtracker.Models;


import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DonationManager {

    private static DonationManager instance = new DonationManager();

    private List<Donation> donations;
    private LocationManager locationManager;


    private DonationManager() {
        this.donations = new ArrayList<>();
        this.locationManager = LocationManager.getInstance();
    }

    public static DonationManager getInstance() {
        return instance;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void addDonation(String name, String location, double value, String shortDescription,
                            String fullDescription, Category category, String comment) {
        Donation donation = new Donation(name, location, value, shortDescription, fullDescription,
                category, comment);
        donations.add(donation);
    }

    public ArrayList<Donation> search(Predicate<Donation> filter) {
        ArrayList<Donation> result = new ArrayList<>();
        for (Donation donation : this.donations) {
            if (filter.test(donation)) {
                result.add(donation);
            }
        }
        return result;
    }

    public ArrayList<Donation> searchByCategory(final Location location, final Category category) {
        if (location.equals(locationManager.getDefaultAllLocation())) {
            return search(new Predicate<Donation>() {
                @Override
                public boolean test(Donation donation) {
                    return donation.checkCategory(category);
                }
            });
        } else {
            return search(new Predicate<Donation>() {
                @Override
                public boolean test(Donation donation) {
                    return donation.checkLocation(location) && donation.checkCategory(category);
                }
            });
        }
    }

    public ArrayList<Donation> searchByName(final Location location, final String name) {
        if (location.equals(locationManager.getDefaultAllLocation())) {
            return search(new Predicate<Donation>() {
                @Override
                public boolean test(Donation donation) {
                    return donation.checkName(name);
                }
            });
        } else {
            return search(new Predicate<Donation>() {
                @Override
                public boolean test(Donation donation) {
                    return donation.checkLocation(location) && donation.checkName(name);
                }
            });
        }
    }


}
