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

        Intent intent = getIntent();
        Location location = (Location) intent.getSerializableExtra("location");

        if (userManager.getCurrentUser().getLocation() != null) {
            int spinnerPosition = locationAdapter.getPosition(location);
            locationSpinner.setSelection(spinnerPosition);
            locationSpinner.setEnabled(false);
        }
    }

    public void onAddClicked(View view) {
        String name = this.name.getText().toString();
        Location location = (Location) locationSpinner.getSelectedItem();
        Double value = getValue();
        String shortDescription = this.shortDescription.getText().toString();
        String fullDescription = this.fullDescription.getText().toString();
        Category category = (Category) categorySpinner.getSelectedItem();
        String comment = this.comment.getText().toString();

        donationManager.addDonation(name, location, value, shortDescription, fullDescription,
                category, comment, photo);

        Intent backtoLocationPageIntent = new Intent(this, PageLocation.class);
        backtoLocationPageIntent.putExtra("location", location);
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

    public double getValue() {
        if (this.value.getText().toString() == null || this.value.getText().toString().equals("")) {
            return 0;
        }
        double value = Double.parseDouble(this.value.getText().toString());
        if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }
}
