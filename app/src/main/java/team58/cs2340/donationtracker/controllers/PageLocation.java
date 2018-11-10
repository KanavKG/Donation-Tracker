package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.Role;
import team58.cs2340.donationtracker.models.CurrUserLocal;
import team58.cs2340.donationtracker.R;

public class PageLocation extends AppCompatActivity {
    private Button addItem;
    private Button locationDetails;
    private Location location;
    ArrayList<Donation> donationsAtLocation = new ArrayList<>();
    ListView donationListView;
    CurrUserLocal userManager;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);

        userManager = CurrUserLocal.getInstance();
        db = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");

        donationListView = findViewById(R.id.donationList);

        db.collection("donations")
                .whereEqualTo("location", location.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot d : task.getResult()) {
                                Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                        d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                        d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                donationsAtLocation.add(donation);
                                ListAdapter locationAdapter = new DonationListAdapter(PageLocation.this, R.layout.layout_donationitem, donationsAtLocation);
                                donationListView.setAdapter(locationAdapter);

                                donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent donationDetailsintent = new Intent(getApplicationContext(), DonationItemDetail.class);
                                        Log.d("donation clicked:", donationsAtLocation.get(position).getName());
                                        donationDetailsintent.putExtra("donation", donationsAtLocation.get(position));
                                        startActivity(donationDetailsintent);
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(PageLocation.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        addItem = findViewById(R.id.addItemBtn);
        if (((userManager.getCurrentUser().getRole() == Role.LOCATIONEMPLOYEE) && userManager.
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
