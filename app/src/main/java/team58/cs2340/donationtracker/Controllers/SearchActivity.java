package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team58.cs2340.donationtracker.Models.Category;
import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationManager;
import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.R;

public class SearchActivity extends AppCompatActivity {


    private LocationManager locationManager;

    private TextView name;
    private Spinner locationSpinner;
    private List<Location> locations;
    private Spinner categorySpinner;
    private ListView donationList;

    private ArrayList<Donation> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.locationManager = LocationManager.getInstance();

        this.name = findViewById(R.id.name);
        this.locationSpinner = findViewById(R.id.locationSpinner);
        this.categorySpinner = findViewById(R.id.categorySpinner);

        locations = locationManager.getLocations();
        locations.add(0, locationManager.getDefaultAllLocation());
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
    }

    public void onSearchByCategory(View view) {
        if (locationSpinner.getSelectedItem() != locationManager.getDefaultAllLocation()) {
            //DonationManager.searchByCategory(locationSpinner.getSelectedItem(),
            //    categorySpinner.getSelectedItem());
        } else {
            //DonationManager.searchByCategory(null, categorySpinner.getSelectedItem();
        }
    }

    public void onSearchByName(View view) {
        if (locationSpinner.getSelectedItem() != locationManager.getDefaultAllLocation()) {
            //DonationManager.searchByName(locationSpinner.getSelectedItem(),
            //    name.getText().toString());
        } else {
            //DonationManager.searchByName(null, name.getText().toString();
        }
    }
}
