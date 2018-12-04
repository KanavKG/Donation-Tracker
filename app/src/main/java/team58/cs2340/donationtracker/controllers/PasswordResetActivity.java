package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import team58.cs2340.donationtracker.R;

public class PasswordResetActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        passwordEmail = (EditText)findViewById(R.id.emailTxt);
        Button resetPassword = (Button) findViewById(R.id.sendBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = passwordEmail.getText().toString().trim();

                if(useremail.equals("")){
                    passwordEmail.setError("Email is required!");
                    passwordEmail.requestFocus();
                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordResetActivity.this,
                                        "Password reset email sent!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordResetActivity
                                        .this, LoginActivity.class));
                            }else{
                                Toast.makeText(PasswordResetActivity.this,
                                        "Error in sending password reset email",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
