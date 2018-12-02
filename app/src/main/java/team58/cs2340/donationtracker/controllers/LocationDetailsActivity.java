package team58.cs2340.donationtracker.controllers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.R;

/**
 * Locations details activity class
 */
public class LocationDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double locLat;
    private double locLng;
    private String locName;
    Location location;
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
        Button directionsBtn = findViewById(R.id.directions);

        Intent intent = getIntent();
        location = (Location) intent.getSerializableExtra("location");

        locLat = Double.parseDouble(location.getLatitude());
        locLng = Double.parseDouble(location.getLongitude());
        locName = location.getName();

        locationTitle.setText(location.getName());
        locationType.setText(location.getType().toString());
        String address = "Address: " + location.getStreetAddress();
        locationAddress.setText(address);
        String phoneNumber = "Phone Number: " + location.getPhoneNumber();
        locationPhone.setText(phoneNumber);
        locationWebsite.setClickable(true);
        locationWebsite.setMovementMethod(LinkMovementMethod.getInstance());
        locationWebsite.setText(Html.fromHtml(
                "Website: " + "<a href='" + location.getWebsite() + "'>"
                        + location.getWebsite() + "</a>"));

        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapFrag);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    public void goTo() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            goWithLocationPerm();
        } else {
            requestLocationPermission();
        }
    }

    public void goWithLocationPerm() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        Criteria criteria = new Criteria();
        String bestProvider = lm.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            android.location.Location loc = lm.getLastKnownLocation(bestProvider);

            Log.d("LOCATION: ",loc.toString());
            String currLat = Double.toString(loc.getLatitude());
            String currLong = Double.toString(loc.getLongitude());

            String url = "http://www.google.com/maps/dir/?api=1" +
                    "&origin=" + currLat + "," + currLong;
            url += "&destination=" + location.getLatitude() + ","
                    + location.getLongitude();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.
                permission.ACCESS_FINE_LOCATION}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goWithLocationPerm();
                } else {
                    Toast.makeText(this, "failed as location permission was denied",
                            Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable vectorDrawable = ContextCompat.getDrawable(
                context, R.drawable.ic_location_on_black_24dp);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth()*2,
                vectorDrawable.getIntrinsicHeight()*2);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth()*2,
                vectorDrawable.getIntrinsicHeight()*2, Bitmap.Config.ARGB_8888);
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
