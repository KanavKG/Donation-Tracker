package team58.cs2340.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginPressed(View view) {
        view.clearFocus();
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        if (username.getText().toString().equals("user") &&
                password.getText().toString().equals("pass")) {
            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
            Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_SHORT).show();
        } else {
            username.setText("");
            password.setText("");
            Toast.makeText(getApplicationContext(), "Check your username and password",Toast.LENGTH_LONG).show();
        }

    }
}