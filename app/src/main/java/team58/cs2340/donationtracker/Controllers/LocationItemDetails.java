package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.R;

public class LocationItemDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_item_details);

        TextView locationTitle = findViewById(R.id.locationTitleTxt);
        TextView locationType = findViewById(R.id.typeTxt);
        TextView locationCoords = findViewById(R.id.coordsTxt);
        TextView locationAddress = findViewById(R.id.streetAddressTxt);
        TextView locationPhone = findViewById(R.id.phoneTxt);

        TextView locationWebsite = findViewById(R.id.websiteTxt);

        Intent intent = getIntent();
        Location location = (Location) intent.getSerializableExtra("location");

        locationTitle.setText(location.getName());
        locationType.setText(location.getType().toString());
        String coords = location.getLatitude() + ", "
                + location.getLongitude();
        locationCoords.setText("Coordinates: " + coords);
        locationAddress.setText("Address: " + location.getStreetAddress());
        locationPhone.setText("Phone Number: " + location.getPhoneNumber());
        locationWebsite.setText("Website: " + location.getWebsite());
    }
}
