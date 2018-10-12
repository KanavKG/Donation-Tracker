package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationType;
import team58.cs2340.donationtracker.R;

public class DonationList extends AppCompatActivity {

    private ArrayList<Location> locations = new ArrayList<>();
    private ListView donationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationlist);
        readLocationData();
        donationListView = (ListView) findViewById(R.id.donationList);
        LocationListAdapter locationAdapter = new LocationListAdapter(this, R.layout.layout_listitem, locations);
        for (Location l : locations) {
            System.out.println(l);
        }
        donationListView.setAdapter(locationAdapter);
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            /*case R.id.logoutBtn:
                Intent logoutIntent = new Intent(this, Welcome.class);
                startActivity(logoutIntent);
                Toast.makeText(getApplicationContext(), "Logout Successful!",Toast.LENGTH_SHORT).show();
                break;*/
        }
    }

    private void readLocationData() {
        InputStream instream = getResources().openRawResource(R.raw.locationdata);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(instream, Charset.forName("UTF-8")));
        String ln;
        try {
            //skip header line
            reader.readLine();
            while ((ln = reader.readLine()) != null) {
                String[] tokens = ln.split(",");
                Location loc = new Location();
                loc.setKey(Integer.parseInt(tokens[0]));
                loc.setName(tokens[1]);
                loc.setLatitude(Double.parseDouble(tokens[2]));
                loc.setLongitude(Double.parseDouble(tokens[3]));
                loc.setStreetAddress(tokens[4]);
                loc.setCity(tokens[5]);
                loc.setState(tokens[6]);
                loc.setZip(tokens[7]);
                if (tokens[8].equals("Drop Off")) loc.setType(LocationType.DROPOFF);
                else if (tokens[8].equals("Store")) loc.setType(LocationType.STORE);
                else if (tokens[8].equals("Warehouse")) loc.setType(LocationType.WAREHOUSE);
                loc.setPhoneNumber(tokens[9]);
                loc.setWebsite(tokens[10]);
                locations.add(loc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
