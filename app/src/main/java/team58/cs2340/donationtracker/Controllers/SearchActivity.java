package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import team58.cs2340.donationtracker.Models.Category;
import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.Models.DonationManager;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationManager;
import team58.cs2340.donationtracker.R;

public class SearchActivity extends AppCompatActivity {


    private LocationManager locationManager;
    private DonationManager donationManager;

    private TextView name;
    private Spinner locationSpinner;
    private List<Location> locations;
    private Spinner categorySpinner;
    private ListView donationList;
    private TextView message;

    private ArrayList<Donation> result;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.locationManager = LocationManager.getInstance();
        this.donationManager = DonationManager.getInstance();
        db = FirebaseFirestore.getInstance();

        this.name = findViewById(R.id.name);
        this.locationSpinner = findViewById(R.id.locationSpinner);
        this.categorySpinner = findViewById(R.id.categorySpinner);

        locations = new ArrayList<>();
        locations.add(locationManager.getDefaultAllLocation());
        locations.addAll(locationManager.getLocations());
        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);

        donationList = findViewById(R.id.donationList);
        result = new ArrayList<>();

        DonationListAdapter donationAdapter = new DonationListAdapter(this, R.layout.layout_donationitem, result);
        donationList.setAdapter(donationAdapter);

        donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, DonationItemDetail.class);
                intent.putExtra("donation", result.get(position));
                startActivity(intent);
            }
        });

        this.message = findViewById(R.id.message);
        message.setVisibility(View.GONE);
    }

    public void onSearchByCategory(View view) {
        message.setVisibility(View.GONE);
        result = donationManager.searchByCategory((Location) locationSpinner.getSelectedItem(),
                (Category) categorySpinner.getSelectedItem());
        DonationListAdapter donationAdapter = new DonationListAdapter(this, R.layout.layout_donationitem, result);
        donationList.setAdapter(donationAdapter);

        db.collection("donations")
                .whereEqualTo("location", ((Location) locationSpinner.getSelectedItem()).getName())
                .whereEqualTo("category", ((Category) categorySpinner.getSelectedItem()).toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot d : task.getResult()) {
                                Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                        d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                        d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                result.add(donation);
                                DonationListAdapter donationAdapter = new DonationListAdapter(SearchActivity.this, R.layout.layout_donationitem, result);
                                donationList.setAdapter(donationAdapter);
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        if (result.size() == 0) {
            message.setVisibility(View.VISIBLE);
        }
    }

    public void onSearchByName(View view) {
        message.setVisibility(View.GONE);
        db.collection("donations")
                .whereEqualTo("location", ((Location) locationSpinner.getSelectedItem()).getName())
                .whereEqualTo("name", name.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot d : task.getResult()) {
                                Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                        d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                        d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                result.add(donation);
                                DonationListAdapter donationAdapter = new DonationListAdapter(SearchActivity.this, R.layout.layout_donationitem, result);
                                donationList.setAdapter(donationAdapter);
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        if (result.size() == 0) {
            message.setVisibility(View.VISIBLE);
        }
    }
}
