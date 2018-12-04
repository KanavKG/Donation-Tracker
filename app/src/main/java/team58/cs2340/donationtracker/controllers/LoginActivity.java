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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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

import java.util.Arrays;
import java.util.List;
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
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 0;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);
        this.mAuth = FirebaseAuth.getInstance();
        this.userManager = CurrUserLocal.getInstance();
        this.db = FirebaseFirestore.getInstance();

        TextView resetPass = findViewById(R.id.forgotPasswordTxt);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,
                        PasswordResetActivity.class));
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Toast.makeText(LoginActivity.this,"Sign in successful! :)"
                        , Toast.LENGTH_SHORT).show();
                userManager.setCurrentUser(new User());
                Intent intent = new Intent(LoginActivity.this,
                        HomeScreenActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(LoginActivity.this,
                        "Cancelled Login",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception)
            {
                Toast.makeText(LoginActivity.this,
                        "Login unsuccessful! :(",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Button guestBtn = findViewById(R.id.guest);
        SignInButton gSignInButton = findViewById(R.id.sign_in_button);
        gSignInButton.setSize(SignInButton.SIZE_STANDARD);
        gSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

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
                                    assert d != null;
                                    if ("Location Employee".equals(
                                            d.getString("role"))) {
                                        userManager.setCurrentUser(
                                                new User(Objects.requireNonNull(d.getString(
                                                        "first")),
                                                        Objects.requireNonNull(d.getString("last")),
                                                        Objects.requireNonNull(Role.Companion.fromString(
                                                                Objects.requireNonNull(d.getString("role")))),
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Toast.makeText(LoginActivity.this,"Signed in as "
                            + account.getEmail() + " :)", Toast.LENGTH_SHORT).show();
            userManager.setCurrentUser(new User());
            Intent intent = new Intent(LoginActivity.this,
                    HomeScreenActivity.class);
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(LoginActivity.this,
                    "Login unsuccessful! :(",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(this, WelcomeScreenActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(a);
    }
}
