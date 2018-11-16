package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.LocationsLocal;
import team58.cs2340.donationtracker.models.Role;
import team58.cs2340.donationtracker.R;


/**
 * Class for register activity
 */
public class RegisterActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private Spinner roleSpinner;
    private Spinner locationSpinner;
    private TextView password;
    private TextView confirmPassword;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        LocationsLocal locationManager = LocationsLocal.getInstance();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        roleSpinner = findViewById(R.id.roleSpinner);
        locationSpinner = findViewById(R.id.locationSpinner);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        ArrayAdapter<Role> roleAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Role.values());
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, locationManager.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        roleSpinner.setOnItemSelectedListener(this);

        locationSpinner.setVisibility(View.GONE);

        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Method executed when registration button is clicked
     * @param view current view
     */
    public void onRegisterClicked(View view) {

        final Location location = (roleSpinner.getSelectedItem() == Role.LOCATIONEMPLOYEE) ?
                (Location) locationSpinner.getSelectedItem() : null;

        String useremail = email.getText().toString().trim();
        String userpass = password.getText().toString().trim();

        if (useremail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if (userpass.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if (!userpass.equals(confirmPassword.getText().toString().trim())) {
            confirmPassword.setError("Passwords do not match!");
            password.requestFocus();
            confirmPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }

        if (userpass.length() < 6) {
            password.setError("Password must be at least 6 characters long!");
            password.requestFocus();
            return;
        }

        final String userfirst = firstName.getText().toString().trim();
        final String userlast = lastName.getText().toString().trim();

        if (userfirst.isEmpty()) {
            firstName.setError("First name required!");
            firstName.requestFocus();
            return;
        }

        if (userlast.isEmpty()) {
            lastName.setError("Last name required!");
            lastName.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(useremail, userpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registered successfully! :)",
                                    Toast.LENGTH_SHORT).show();

                            //FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, Object> user = new HashMap<>();
                            user.put("UID", Objects.requireNonNull(
                                    mAuth.getCurrentUser()).getUid());
                            user.put("first", userfirst);
                            user.put("last", userlast);
                            user.put("role", roleSpinner.getSelectedItem().toString());
                            if (roleSpinner.getSelectedItem() == Role.LOCATIONEMPLOYEE) {
                                assert location != null;
                                user.put("location", location.getName());
                            }

                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(
                                            new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(RegisterActivity.this,
                                                    "Added user to database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this,
                                                    "Failed to add user to database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this,
                                        "An account with that email already exists",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, Objects.requireNonNull(
                                        task.getException()).getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    /**
     * Function executed when role = location employee is selected
     * @param parent ListAdapter
     * @param view view of the current scene
     * @param pos Position of item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (parent.getItemAtPosition(pos) == Role.LOCATIONEMPLOYEE) {
            locationSpinner.setVisibility(View.VISIBLE);
        } else {
            locationSpinner.setVisibility(View.GONE);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
