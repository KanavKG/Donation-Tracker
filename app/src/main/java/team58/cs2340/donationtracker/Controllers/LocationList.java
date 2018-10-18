package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
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
import java.nio.charset.Charset;
import java.util.ArrayList;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationType;
import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.R;

public class LocationList extends AppCompatActivity {

    private ArrayList<Location> locations = new ArrayList<>();
    private ListView donationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Model model = Model.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlist);
        donationListView = findViewById(R.id.donationList);
        LocationListAdapter locationAdapter = new LocationListAdapter(this, R.layout.layout_locationitem, model.getLocations());
        donationListView.setAdapter(locationAdapter);

        donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LocationItemDetails.class);
                intent.putExtra("name", locations.get(position).getName());
                intent.putExtra("type", locations.get(position).getType().getTypeName());
                intent.putExtra("coords", "Coordinates: " + Double.toString(
                        locations.get(position).getLatitude()) + ", " + Double.toString(
                                locations.get(position).getLongitude()));
                intent.putExtra("address", "Address: " + locations.get(position).
                        getStreetAddress() + ", " + locations.get(position).getCity() + ", " +
                        locations.get(position).getZip());
                intent.putExtra("phone", locations.get(position).getPhoneNumber());
                intent.putExtra("website", locations.get(position).getWebsite());
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
                Toast.makeText(getApplicationContext(), "Logout Successful!",Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
