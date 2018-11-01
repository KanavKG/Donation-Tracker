package team58.cs2340.donationtracker.Controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import team58.cs2340.donationtracker.Models.Category;
import team58.cs2340.donationtracker.Models.DonationManager;
import team58.cs2340.donationtracker.Models.LocationManager;
import team58.cs2340.donationtracker.Models.Model;
import team58.cs2340.donationtracker.Models.Donation;
import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.Models.UserManager;
import team58.cs2340.donationtracker.R;

public class AddDonation extends AppCompatActivity {

    private DonationManager donationManager;
    private LocationManager locationManager;
    private UserManager userManager;

    private TextView name;
    private Spinner locationSpinner;
    private TextView value;
    private TextView shortDescription;
    private TextView fullDescription;
    private Spinner categorySpinner;
    private TextView comment;
    private Bitmap photo;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView photoView;
    Button takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        this.donationManager = DonationManager.getInstance();
        this.locationManager = LocationManager.getInstance();
        this.userManager = UserManager.getInstance();
        this.name = findViewById(R.id.name);
        this.locationSpinner = findViewById(R.id.locationSpinner);
        this.shortDescription = findViewById(R.id.shortDescription);
        this.fullDescription = findViewById(R.id.fullDescription);
        this.value = findViewById(R.id.value);
        this.categorySpinner = findViewById(R.id.categorySpinner);
        this.comment = findViewById(R.id.comment);
        this.takePhoto = findViewById(R.id.takePhotoBtn);
        this.photoView = findViewById(R.id.photo);

        //Disable button if user has no camera
        if (!hasCamera()) {
            takePhoto.setEnabled(false);
            takePhoto.setVisibility(View.GONE);
        }

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationManager.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);

        if (userManager.getCurrentUser().getLocation() != null) {
            int spinnerPosition = locationAdapter.getPosition(userManager.getCurrentUser().getLocation());
            locationSpinner.setSelection(spinnerPosition);
            locationSpinner.setEnabled(false);
        }
    }

    public void onAddClicked(View view) {
        String n = name.getText().toString();
        Location loc = (Location) locationSpinner.getSelectedItem();
        Double val = Double.parseDouble(value.getText().toString());
        String sDes = shortDescription.getText().toString();
        String fDes = fullDescription.getText().toString();
        Category cat = (Category) categorySpinner.getSelectedItem();
        String com = comment.getText().toString();

        Donation donation = new Donation(n, loc, val, sDes, fDes, cat, com, photo);
        donationManager.addDonation(donation);

        Intent backtoLocationPageIntent = new Intent(this, PageLocation.class);
        backtoLocationPageIntent.putExtra("location", loc);
        startActivity(backtoLocationPageIntent);
    }

    public boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void launchCamera(View view) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");
            photoView.setImageBitmap(photo);
        }
    }
}
