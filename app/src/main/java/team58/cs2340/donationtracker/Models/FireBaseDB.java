package team58.cs2340.donationtracker.Models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FireBaseDB {

    private static final FireBaseDB instance = new FireBaseDB();
    private static final String TAG = "FirebaseDB";

    private FirebaseFirestore db;

    public FireBaseDB() {
        db = FirebaseFirestore.getInstance();
    }

    public static FireBaseDB getInstance() {
        return instance;
    }

    public void addUser(User user) {

        // Add a new document with auto generated ID
        db.collection("users").document(user.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Log.d("FirebaseDB", "user added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirebaseDB", "Error adding user", e);
                    }
                });
        Log.d(TAG, "adding users" + user.getEmail());
    }

    public HashMap<String, User> loadUsers() {
        final HashMap<String, User> users = new HashMap<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FirebaseDB", document.getId() + " => " + document.getData());
                                User user = document.toObject(User.class);
                                users.put(user.getEmail(), user);
                                Log.d(TAG, "Getting Users: " + users.size());
                            }
                        } else {
                            Log.d("FirebaseDB", "Error getting users: ", task.getException());
                        }
                    }
                });

        Log.d("FirebaseDB", "load Users: " + users.size());

        return users;
    }

    public void addLocation(Location location) {

        // Add a new document with auto generated ID
        db.collection("locations").document("" + location.getKey())
                .set(location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Log.d("FirebaseDB", "location added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirebaseDB", "Error adding location", e);
                    }
                });
        Log.d(TAG, "adding location: " + location.getName());
    }

    public List<Location> loadLocations() {
        final List<Location> locations = new ArrayList<>();
        db.collection("locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FirebaseDB", document.getId() + " => " + document.getData());
                                Location location = document.toObject(Location.class);
                                locations.add(location);
                                //Log.d(TAG, "Getting Locations: " + locations.size());
                            }
                        } else {
                            Log.d("FirebaseDB", "Error getting users: ", task.getException());
                        }
                    }
                });

        return locations;
    }

    public void addDonation(Donation donation) {

        // Add a new document with auto generated ID
        db.collection("donations")
                .add(donation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        Log.d(TAG, "adding location: " + donation.getName());
    }

    public List<Donation> loadDonations() {
        final List<Donation> donations = new ArrayList<>();
        db.collection("donations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FirebaseDB", document.getId() + " => " + document.getData());
                                Donation donation = document.toObject(Donation.class);
                                donations.add(donation);
                                Log.d(TAG, "Getting Locations: " + donations.size());
                            }
                        } else {
                            Log.d("FirebaseDB", "Error getting users: ", task.getException());
                        }
                    }
                });

        return donations;
    }

}
