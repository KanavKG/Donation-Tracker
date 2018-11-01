package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.Models.UserManager;
import team58.cs2340.donationtracker.R;

public class Login extends AppCompatActivity {

    private TextView email;
    private TextView password;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.email = findViewById(R.id.email);
        this.password = findViewById(R.id.password);

        this.userManager = UserManager.getInstance();

    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login:
                v.clearFocus();
                if (userManager.validLogin(this.email.getText().toString(),
                        this.password.getText().toString())) {
                    Intent intent = new Intent(this, LocationList.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Welcome",Toast.LENGTH_SHORT).show();
                } else {
                    password.setText("");
                    Toast.makeText(getApplicationContext(), "Username and/or password incorrect!",Toast.LENGTH_LONG).show();
                }
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
