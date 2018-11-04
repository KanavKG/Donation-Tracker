package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.Models.DonationManager;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.Role;
import team58.cs2340.donationtracker.Models.UserManager;
import team58.cs2340.donationtracker.R;

public class PageLocation extends AppCompatActivity {
    private Button addItem;
    private Button locationDetails;
    private Location location;
    ArrayList<Donation> donationsAtLocation = new ArrayList<>();
    ArrayAdapter<Donation> adapter;
    ListView donationListView;
    UserManager userManager;

    private DonationManager donationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);

        userManager = UserManager.getInstance();



        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");
        
        this.donationManager = DonationManager.getInstance();
        for (Donation donation : donationManager.getDonations()) {
            if (donation.getLocation().getKey() == location.getKey()) {
                donationsAtLocation.add(donation);
            }
        }
        donationListView = findViewById(R.id.donationList);
        DonationListAdapter locationAdapter = new DonationListAdapter(this, R.layout.layout_donationitem, donationsAtLocation);
        donationListView.setAdapter(locationAdapter);

        donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PageLocation.this, DonationItemDetail.class);
                intent.putExtra("donation", donationManager.getDonations().get(position));
                startActivity(intent);
            }
        });
        

        addItem = findViewById(R.id.addItemBtn);
        if ((userManager.getCurrentUser().getRole() == Role.LOCATIONEMPLOYEE && userManager.
                getCurrentUser().getLocation().equals(location.getName()))) {
            addItem.setVisibility(View.VISIBLE);
            addItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent addItemIntent = new Intent(PageLocation.this, AddDonation.class);
                    addItemIntent.putExtra("location", location);
                    startActivity(addItemIntent);
                }
            });
        } else {
            addItem.setVisibility(View.GONE);
        }

        locationDetails = findViewById(R.id.locationDetailsBtn);
        locationDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent detailsIntent = new Intent(PageLocation.this, LocationItemDetails.class);
                detailsIntent.putExtra("location", location);
                startActivity(detailsIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, LocationList.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(a);
    }

}
