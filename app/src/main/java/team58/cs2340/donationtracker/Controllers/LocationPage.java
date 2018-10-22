package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.Models.Role;
import team58.cs2340.donationtracker.R;

public class LocationPage extends AppCompatActivity {

    private Button addItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);
        Model model = Model.getInstance();
        addItem = findViewById(R.id.addItemBtn);
        if (model.getCurrentUser().getRole() == Role.LOCATIONEMPLOYEE ||
                model.getCurrentUser().getRole() == Role.ADMIN)
            addItem.setVisibility(View.VISIBLE);
    }
}
