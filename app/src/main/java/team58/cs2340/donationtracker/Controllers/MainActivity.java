package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    private List<Location> locations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                loc.setType(LocationType.valueOf(tokens[0]));
                loc.setPhoneNumber(tokens[0]);
                loc.setWebsite(tokens[0]);
                locations.add(loc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
