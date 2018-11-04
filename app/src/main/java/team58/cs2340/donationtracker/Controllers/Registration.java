package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.LocationType;
import team58.cs2340.donationtracker.Models.UserManager;
import team58.cs2340.donationtracker.Models.LocationManager;
import team58.cs2340.donationtracker.Models.User;
import team58.cs2340.donationtracker.Models.Role;
import team58.cs2340.donationtracker.R;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private Spinner roleSpinner;
    private Spinner locationSpinner;
    private TextView password;
    private TextView confirmPassword;

    private UserManager userManager;
    private LocationManager locationManager;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        this.userManager = UserManager.getInstance();
        this.locationManager = LocationManager.getInstance();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        roleSpinner = findViewById(R.id.roleSpinner);
        locationSpinner = findViewById(R.id.locationSpinner);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        ArrayAdapter<Role> roleAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Role.values());
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationManager.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        roleSpinner.setOnItemSelectedListener(this);

        locationSpinner.setVisibility(View.GONE);

        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getToken();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

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
                            Toast.makeText(Registration.this, "Registered successfully! :)",
                                    Toast.LENGTH_SHORT).show();

                            //FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, Object> user = new HashMap<>();
                            user.put("UID", mAuth.getCurrentUser().getUid());
                            user.put("first", userfirst);
                            user.put("last", userlast);
                            user.put("role", ((Role) roleSpinner.getSelectedItem()).toString());
                            if ((Role) roleSpinner.getSelectedItem() == Role.LOCATIONEMPLOYEE) {
                                user.put("location", location.getName());
                            }

                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(Registration.this, "Added user to database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Registration.this, "Failed to add user to database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            Intent intent = new Intent(Registration.this, Login.class);
                            startActivity(intent);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(Registration.this, "An account with that email already exists",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Registration.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (parent.getItemAtPosition(pos) == Role.LOCATIONEMPLOYEE) {
            locationSpinner.setVisibility(View.VISIBLE);
        } else {
            locationSpinner.setVisibility(View.GONE);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
