package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import team58.cs2340.donationtracker.models.Role;
import team58.cs2340.donationtracker.models.User;
import team58.cs2340.donationtracker.models.CurrUserLocal;
import team58.cs2340.donationtracker.R;

/**
 * Class for login activity
 */
public class LoginActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private FirebaseAuth mAuth;
    private CurrUserLocal userManager;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.mAuth = FirebaseAuth.getInstance();
        this.userManager = CurrUserLocal.getInstance();
        this.db = FirebaseFirestore.getInstance();

        Button guestBtn = findViewById(R.id.guest);

        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Sign in successful! :)",
                        Toast.LENGTH_SHORT).show();
                userManager.setCurrentUser(new User());
                Intent intent = new Intent(LoginActivity.this,
                        HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Function called when a button is pressed
     * @param v view of the login page
     */
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login:
                v.clearFocus();
                final String useremail = email.getText().toString().trim();
                final String userpass = password.getText().toString().trim();
                final int[] unsuccessfulLoginAttempts = new int[1];

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

                final DocumentReference userRef = db.collection(
                        "users").document(useremail);

                userRef.get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot d = task.getResult();
                                    if ("Location Employee".equals(
                                            d.getString("role"))) {
                                        userManager.setCurrentUser(
                                                new User(d.getString(
                                                        "first"),
                                                        d.getString("last"),
                                                        Role.fromString(
                                                                d.getString("role")),
                                                        d.getString("location")));
                                    } else {
                                        userManager.setCurrentUser(new User());
                                    }
                                    unsuccessfulLoginAttempts[0] = Objects.requireNonNull(d.getLong(
                                            "unsuccessfulLoginAttempts")).intValue();
                                    if (unsuccessfulLoginAttempts[0] > 2) {
                                        Toast.makeText(LoginActivity.this,
                                                "Too many incorrect attempts! \n Account locked :(",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        mAuth.signInWithEmailAndPassword(useremail, userpass).
                                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(LoginActivity.this,"Sign in successful! :)",
                                                                    Toast.LENGTH_SHORT).show();
                                                            userManager.setCurrentUser(new User());
                                                            Intent intent = new Intent(LoginActivity.this,
                                                                    HomeScreenActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            userRef.update("unsuccessfulLoginAttempts",
                                                                    unsuccessfulLoginAttempts[0] + 1)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            if (unsuccessfulLoginAttempts[0] == 0) {
                                                                                Toast.makeText(LoginActivity.this,
                                                                                        "Login unsuccessful! :( \n You have 2" +
                                                                                                " more tries!",
                                                                                        Toast.LENGTH_SHORT).show();
                                                                            } else if (unsuccessfulLoginAttempts[0] == 1) {
                                                                                Toast.makeText(LoginActivity.this,
                                                                                        "Login unsuccessful! :( \n You have 1" +
                                                                                                " more try!",
                                                                                        Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(LoginActivity.this,
                                                                                    e.getMessage(),
                                                                                    Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this,
                                            Objects.requireNonNull(
                                                    task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, WelcomeScreenActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(a);
    }
}
