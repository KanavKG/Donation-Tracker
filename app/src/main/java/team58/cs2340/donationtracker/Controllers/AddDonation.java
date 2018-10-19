package team58.cs2340.donationtracker.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.R;

public class AddDonation extends AppCompatActivity {

    private Model model;

    private TextView name;
    private TextView shortDescription;
    private TextView fullDescription;
    private TextView value;
    private TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        /*this.model = Model.getInstance();
        this.name = findViewById(R.id.name);
        this.shortDescription = findViewById(R.id.shortDescription);
        this.fullDescription = findViewById(R.id.fullDescription);
        this.value = findViewById(R.id.value);
        this.comment = findViewById(R.id.comment);*/
    }

    public void onAddClicked(View view) {
        /*Donation donation = new Donation(model.getCurrentUser().getLocation(),
                name.getText().toString(), shortDescription.getText().toString(),
                fullDescription.getText().toString())*/
    }
}
