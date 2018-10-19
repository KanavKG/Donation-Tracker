package team58.cs2340.donationtracker.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.R;

public class AddDonation extends AppCompatActivity {

    private Model model;

    private TextView name;
    private Spinner locationSpinner;
    private TextView value;
    private TextView shortDescription;
    private TextView fullDescription;
    private TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        this.model = Model.getInstance();
        this.name = findViewById(R.id.name);
        this.locationSpinner = findViewById(R.id.locationSpinner);
        this.shortDescription = findViewById(R.id.shortDescription);
        this.fullDescription = findViewById(R.id.fullDescription);
        this.value = findViewById(R.id.value);
        this.comment = findViewById(R.id.comment);

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, model.getLocations());
        locationSpinner.setAdapter(locationAdapter);
    }

    public void onAddClicked(View view) {
        String n = name.getText().toString();
        Location loc = (Location) locationSpinner.getSelectedItem();
        Double val = Double.parseDouble(value.getText().toString());
        String sDes = shortDescription.getText().toString();
        String fDes = fullDescription.getText().toString();
        String com = comment.getText().toString();

        Donation donation = new Donation(n, loc, val, sDes, fDes, com);
        model.addDonation(donation);
    }
}
