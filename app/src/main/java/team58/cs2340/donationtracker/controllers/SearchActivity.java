package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.LocationsLocal;
import team58.cs2340.donationtracker.R;

public class SearchActivity extends AppCompatActivity {

    private static final int THRESHOLD_RATIO = 60;

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
        locations.add(LocationsLocal.getDefaultAllLocation());
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

        if ("All Locations".equals(location) && "All Categories".equals(category)) {
            db.collection("donations")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : Objects.requireNonNull(task.getResult())) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= THRESHOLD_RATIO) {
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
                                if (donationsQueryResults.isEmpty()) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                ListAdapter donationAdapter = new DonationListAdapter(SearchActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchActivity.this, DonationDetailsActivity.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if ("All Locations".equals(location)) {
            db.collection("donations")
                    .whereEqualTo("category", category)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : Objects.requireNonNull(task.getResult())) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= THRESHOLD_RATIO) {
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
                                if (donationsQueryResults.isEmpty()) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                ListAdapter donationAdapter = new DonationListAdapter(SearchActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchActivity.this, DonationDetailsActivity.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        } else if ("All Categories".equals(category)) {
            db.collection("donations")
                    .whereEqualTo("location", location)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot d : Objects.requireNonNull(task.getResult())) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= THRESHOLD_RATIO) {
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
                                if (donationsQueryResults.isEmpty()) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                ListAdapter donationAdapter = new DonationListAdapter(SearchActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchActivity.this, DonationDetailsActivity.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
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
                                for (QueryDocumentSnapshot d : Objects.requireNonNull(task.getResult())) {
                                    if (FuzzySearch.weightedRatio(searchQuery, d.getString("name")) >= THRESHOLD_RATIO) {
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
                                if (donationsQueryResults.isEmpty()) {
                                    nfMessageText.setVisibility(View.VISIBLE);
                                }
                                ListAdapter donationAdapter = new DonationListAdapter(SearchActivity.this, R.layout.layout_donationitem, donationsQueryResults);
                                donationList.setAdapter(donationAdapter);

                                donationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(SearchActivity.this, DonationDetailsActivity.class);
                                        intent.putExtra("donation", donationsQueryResults.get(position));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                Toast.makeText(SearchActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
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
