package team58.cs2340.donationtracker.Models;

import java.util.ArrayList;
import java.util.List;

public class DonationManager {

    private static DonationManager instance = new DonationManager();

    private List<Donation> donations;

    private DonationManager() {
        this.donations = new ArrayList<>();
    }

    public static DonationManager getInstance() {
        return instance;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void addDonation(Donation donation) {
        donations.add(donation);
    }
}
