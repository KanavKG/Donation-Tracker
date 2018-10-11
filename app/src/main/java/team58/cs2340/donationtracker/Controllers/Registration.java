package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.Models.User;
import team58.cs2340.donationtracker.Models.Role;
import team58.cs2340.donationtracker.R;

public class Registration extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private Spinner roleSpinner;
    private TextView password;
    private TextView confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        roleSpinner = findViewById(R.id.roleSpinner);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        ArrayAdapter<Role> roleAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Role.values());
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);
    }

    public void onRegisterClicked(View view) {
        Model model = Model.getInstance();
        User user = new User(firstName.getText().toString(), lastName.getText().toString(),
                email.getText().toString(), password.getText().toString(),
                (Role) roleSpinner.getSelectedItem());
        if (model.addUser(email.getText().toString(), user)) {
            Toast.makeText(getApplicationContext(), "An account with that email already exists",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }
}
