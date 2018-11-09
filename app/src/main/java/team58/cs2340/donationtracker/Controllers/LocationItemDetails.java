package team58.cs2340.donationtracker.Controllers;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.R;

public class LocationItemDetails extends AppCompatActivity implements OnMapReadyCallback {

    private double locLat, locLng;
    private String locName;

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

        locLat = Double.parseDouble(location.getLatitude());
        locLng = Double.parseDouble(location.getLongitude());
        locName = location.getName();

        locationTitle.setText(location.getName());
        locationType.setText(location.getType().toString());
        String coords = location.getLatitude() + ", "
                + location.getLongitude();
        locationCoords.setText("Coordinates: " + coords);
        locationAddress.setText("Address: " + location.getStreetAddress());
        locationPhone.setText("Phone Number: " + location.getPhoneNumber());
        locationWebsite.setText("Website: " + location.getWebsite());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap locMap) {
        LatLng locationPos = new LatLng(locLat, locLng);
        locMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPos, 16.0f));
        locMap.addMarker(new MarkerOptions().position(locationPos).title(locName));
    }
}
