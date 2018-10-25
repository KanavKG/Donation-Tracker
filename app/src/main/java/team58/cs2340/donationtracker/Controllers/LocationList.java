package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationType;
import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.R;

public class LocationList extends AppCompatActivity{

    private ArrayList<Location> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Model model = Model.getInstance();
        locations = model.getLocations();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlist);
        ListView locationListView = findViewById(R.id.locationList);
        LocationListAdapter locationAdapter = new LocationListAdapter(this, R.layout.layout_locationitem, locations);
        locationListView.setAdapter(locationAdapter);

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocationList.this, PageLocation.class);
                intent.putExtra("location", locations.get(position));
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.logoutBtn:
                Intent logoutIntent = new Intent(this, Welcome.class);
                startActivity(logoutIntent);
                Model.getInstance().clearCurrentUser();
                Toast.makeText(getApplicationContext(), "Logout Successful!",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
