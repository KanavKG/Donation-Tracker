package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.ToStringFunction;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import team58.cs2340.donationtracker.Models.Category;
import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationsLocal;
import team58.cs2340.donationtracker.R;

public class SearchDonationsActivity extends AppCompatActivity {

    private static final int WEIGHT = 60;

    private Spinner locationSpinner;
    private Spinner categorySpinner;
    private EditText searchTxt;
    private ListView donationList;
    private TextView nfMessageText;

    private LocationsLocal locationManager;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donations);

        locationSpinner = findViewById(R.id.locationSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        searchTxt = findViewById(R.id.searchText);
        donationList = findViewById(R.id.donationSearchList);
        nfMessageText = findViewById(R.id.nothingFoundText);
        nfMessageText.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();
        LocationsLocal locationManager = LocationsLocal.getInstance();
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(locationManager.getDefaultAllLocation());
        locations.addAll(locationManager.getLocations());
        ArrayList<String> categories = new ArrayList<>();
        categories.add("All Categories");
        for (Category c : Category.values()) {
            categories.add(c.toString());
        }

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        locationSpinner.setAdapter(locationAdapter);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categorySpinner.setAdapter(categoryAdapter);

    }

    /**
     * Function called when search button is clicked
     * @param view view of the search screen
     */
    public void onSearchClick(View view) {
        nfMessageText.setVisibility(View.GONE);
        String location = ((Location) locationSpinner.getSelectedItem()).getName();
        String category = (String) categorySpinner.getSelectedItem();
        final String searchQuery = searchTxt.getText().toString();

        //final ArrayList<Donation> donationsFiltered = new ArrayList<>();
        //final DonationNameToStringFunc dnFunc = new DonationNameToStringFunc();
        final ArrayList<Donation> donationsQueryResults = new ArrayList<>();

        if (location.equals("All Locations") && category.equals("All Categories")) {
            db.collection("donations")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : task.getResult()) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= WEIGHT) {
                                        Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                                d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                                d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                        donationsQueryResults.add(donation);
                                    }

                                    /*Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                            d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                            d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                                }
                                /*List<BoundExtractedResult<Donation>> matches = FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                                if (donationsQueryResults.size() == 0) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                DonationListAdapter donationAdapter = new DonationListAdapter(SearchDonationsActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchDonationsActivity.this, DonationItemDetail.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchDonationsActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if (location.equals("All Locations")) {
            db.collection("donations")
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : task.getResult()) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= WEIGHT) {
                                        Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                                d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                                d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                        donationsQueryResults.add(donation);
                                    }

                                    /*Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                            d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                            d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                                }
                                /*List<BoundExtractedResult<Donation>> matches = FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                                if (donationsQueryResults.size() == 0) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                DonationListAdapter donationAdapter = new DonationListAdapter(SearchDonationsActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchDonationsActivity.this, DonationItemDetail.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchDonationsActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if (category.equals("All Categories")) {
            db.collection("donations")
                    .whereEqualTo("location", location)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : task.getResult()) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= WEIGHT) {
                                        Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                                d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                                d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                        donationsQueryResults.add(donation);
                                    }

                                    /*Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                            d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                            d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                                }
                                /*List<BoundExtractedResult<Donation>> matches = FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                                if (donationsQueryResults.size() == 0) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                DonationListAdapter donationAdapter = new DonationListAdapter(SearchDonationsActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchDonationsActivity.this, DonationItemDetail.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchDonationsActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            db.collection("donations")
                    .whereEqualTo("location", location)
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : task.getResult()) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= WEIGHT) {
                                        Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                                d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                                d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                        donationsQueryResults.add(donation);
                                    }

                                    /*Donation donation = new Donation(d.getDate("timestamp"), d.getString("name"),
                                            d.getString("location"), Double.parseDouble(d.getString("value")), d.getString("shortDescription"),
                                            d.getString("fullDescription"), Category.fromString(d.getString("category")), d.getString("comment"));
                                    donationsFiltered.add(donation);*/
                                }
                                /*List<BoundExtractedResult<Donation>> matches = FuzzySearch.extractTop(searchQuery, donationsFiltered, dnFunc, 5);
                                for (BoundExtractedResult result : matches) {
                                    donationsQueryResults.add((Donation) result.getReferent());
                                }*/
                                if (donationsQueryResults.size() == 0) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                DonationListAdapter donationAdapter = new DonationListAdapter(SearchDonationsActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchDonationsActivity.this, DonationItemDetail.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchDonationsActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    /*class DonationNameToStringFunc implements ToStringFunction<Donation> {
        @Override
        public String apply(Donation item) {
            return item.getName();
        }
    }*/
}
