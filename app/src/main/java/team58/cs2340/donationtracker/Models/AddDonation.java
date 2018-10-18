package team58.cs2340.donationtracker.Models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import team58.cs2340.donationtracker.R;

public class AddDonation extends AppCompatActivity {

    private Model model;

    private TextView name;
    private TextView shortDescription;
    private TextView fullDescription;
    //private add field for monetary value (double)
    private TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        this.model = Model.getInstance();
    }

    public void onAddClicked(View view) {

    }
}
