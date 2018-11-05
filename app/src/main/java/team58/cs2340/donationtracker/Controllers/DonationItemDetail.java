package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.R;

public class DonationItemDetail extends AppCompatActivity {

    private TextView name;
    private TextView location;
    private TextView value;
    private TextView shortDescription;
    private TextView fullDescription;
    private TextView comment;
    private TextView timeStamp;
    //ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_item_detail);

        this.name = findViewById(R.id.name);
        this.location = findViewById(R.id.location);
        this.shortDescription = findViewById(R.id.shortDescription);
        this.fullDescription = findViewById(R.id.fullDescription);
        this.value = findViewById(R.id.value);
        this.comment = findViewById(R.id.comment);
        //this.timeStamp = findViewById(R.id.timeStamp);
        //this.photoView = findViewById(R.id.photoImage);

        Intent intent = getIntent();
        Donation donation  = (Donation) intent.getSerializableExtra("donation");

        name.setText("Name: " + donation.getName());
        location.setText(donation.getLocation().toString());
        value.setText("Value: " + donation.getValue());
        shortDescription.setText("Short Description: " + donation.getShortDescription());
        fullDescription.setText("Full Description: " + donation.getFullDescription());
        comment.setText("Comment: " + donation.getComment());
        //timeStamp.setText("Time Stamp: " + donation.getTimeStamp());
       // photoView.setImageBitmap(donation.getPhoto());
    }
}
