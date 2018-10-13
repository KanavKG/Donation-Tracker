package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import team58.cs2340.donationtracker.R;

public class DonationItemDetails extends AppCompatActivity {

    private TextView locationTitle;
    private TextView locationType;
    private TextView locationCoords;
    private TextView locationAddress;
    private TextView locationPhone;
    private TextView locationWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_item_details);

        locationTitle = findViewById(R.id.locationTitleTxt);
        locationType = findViewById(R.id.typeTxt);
        locationCoords = findViewById(R.id.coordsTxt);
        locationAddress = findViewById(R.id.streetAddressTxt);
        locationPhone = findViewById(R.id.phoneTxt);
        locationWebsite = findViewById(R.id.websiteTxt);

        Intent intent = getIntent();
        locationTitle.setText(intent.getStringExtra("name"));
        locationType.setText(intent.getStringExtra("type"));
        locationCoords.setText(intent.getStringExtra("coords"));
        locationAddress.setText(intent.getStringExtra("address"));
        locationPhone.setText(intent.getStringExtra("phone"));
        locationWebsite.setText(intent.getStringExtra("website"));
    }
}
