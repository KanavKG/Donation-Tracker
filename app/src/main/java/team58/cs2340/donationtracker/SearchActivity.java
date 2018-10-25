package team58.cs2340.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Category;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.Model;

public class SearchActivity extends AppCompatActivity {


    private Model model;

    private TextView name;
    private Spinner locationSpinner;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.model = Model.getInstance();

        this.name = findViewById(R.id.name);
        this.locationSpinner = findViewById(R.id.locationSpinner);
        this.categorySpinner = findViewById(R.id.categorySpinner);

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, model.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);
    }

    public void onSearchClicked(View view) {

    }
}
