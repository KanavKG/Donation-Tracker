package team58.cs2340.donationtracker.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, R.drawable.ic_location_on_black_24dp);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapReady(GoogleMap locMap) {
        LatLng locationPos = new LatLng(locLat, locLng);
        locMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPos, MAP_BUFFER));
        locMap.addMarker(new MarkerOptions().position(locationPos).title(locName)
                .icon(bitmapDescriptorFromVector(this)));
    }
}
