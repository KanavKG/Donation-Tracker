package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.R;

public class LocationItemDetails extends AppCompatActivity implements OnMapReadyCallback {

    private double locLat;
    private double locLng;
    private String locName;
    private static final int MAP_BUFFER = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_item_details);

        TextView locationTitle = findViewById(R.id.locationTitleTxt);
        TextView locationType = findViewById(R.id.typeTxt);
        TextView locationAddress = findViewById(R.id.streetAddressTxt);
        TextView locationPhone = findViewById(R.id.phoneTxt);
        TextView locationWebsite = findViewById(R.id.websiteTxt);

        Intent intent = getIntent();
        Location location = (Location) intent.getSerializableExtra("location");

        locLat = Double.parseDouble(location.getLatitude());
        locLng = Double.parseDouble(location.getLongitude());
        locName = location.getName();

        locationTitle.setText(location.getName());
        locationType.setText(location.getType().toString());
        locationAddress.setText("Address: " + location.getStreetAddress());
        locationPhone.setText("Phone Number: " + location.getPhoneNumber());
        locationWebsite.setClickable(true);
        locationWebsite.setMovementMethod(LinkMovementMethod.getInstance());
        locationWebsite.setText(Html.fromHtml("Website: " + "<a href='" + location.getWebsite() + "'>" + location.getWebsite() + "</a>"));

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap locMap) {
        LatLng locationPos = new LatLng(locLat, locLng);
        locMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPos, MAP_BUFFER));
        locMap.addMarker(new MarkerOptions().position(locationPos).title(locName));
    }
}
