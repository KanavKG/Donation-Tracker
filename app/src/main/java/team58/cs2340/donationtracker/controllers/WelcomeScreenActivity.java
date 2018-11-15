package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.LocationsLocal;
import team58.cs2340.donationtracker.models.LocationType;
import team58.cs2340.donationtracker.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    LocationsLocal locationManager;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        locationManager = LocationsLocal.getInstance();
        db = FirebaseFirestore.getInstance();
        readLocationData();
    }

    /**
     * Onclick listener on welcome page
     * @param v view of the welcome page
     */
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.loginBtn:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.signupBtn:
                Intent registrationIntent = new Intent(this, RegisterActivity.class);
                startActivity(registrationIntent);
                Toast.makeText(WelcomeScreenActivity.this, "Register", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Function to read location data
     */
    private void readLocationData() {
        db.collection("locations").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list) {
                                Location loc = new Location(Integer.parseInt(d.getString("key")), d.getString("name"),
                                        d.getString("latitude"), d.getString("longitude"),
                                        d.getString("streetAddress"), d.getString("city"), d.getString("state"),
                                        d.getString("zip"), LocationType.fromString(d.getString("type")), d.getString("phone"),
                                        d.getString("website"));
                                if(!locationManager.getLocations().contains(loc)) {
                                    locationManager.addLocation(loc);
                                }
                            }
                        }
                    }
                });
    }
}
