package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.Models.Role;
import team58.cs2340.donationtracker.R;

public class PageLocation extends AppCompatActivity {
    private Button addItem;
    private Button locationDetails;
    private Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_location);
        Model model = Model.getInstance();

        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");

        addItem = findViewById(R.id.addItemBtn);
        if ((model.getCurrentUser().getRole() == Role.LOCATIONEMPLOYEE && model.getCurrentUser().getLocation().getKey() == location.getKey())
                || model.getCurrentUser().getRole() == Role.ADMIN) {
            addItem.setVisibility(View.VISIBLE);
            addItem.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent addItemIntent = new Intent(PageLocation.this, AddDonation.class);
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

}
