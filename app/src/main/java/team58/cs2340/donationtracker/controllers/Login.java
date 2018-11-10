package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import team58.cs2340.donationtracker.models.Role;
import team58.cs2340.donationtracker.models.User;
import team58.cs2340.donationtracker.models.CurrUserLocal;
import team58.cs2340.donationtracker.R;

public class Login extends AppCompatActivity {

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

                mAuth.signInWithEmailAndPassword(useremail, userpass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Sign in successful! :)",
                                    Toast.LENGTH_SHORT).show();
                            db.collection("users")
                                    .whereEqualTo("UID", FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (DocumentSnapshot d : task.getResult()) {
                                                    if ("Location Employee".equals(d.getString("role"))) {
                                                        userManager.setCurrentUser(new User(d.getString("first"), d.getString("last"),
                                                                Role.fromString(d.getString("role")), d.getString("location")));
                                                    } else {
                                                        userManager.setCurrentUser(new User());
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(Login.this, task.getException().getMessage(),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            userManager.setCurrentUser(new User());
                            Intent intent = new Intent(Login.this, LocationList.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, Welcome.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(a);
    }
}
