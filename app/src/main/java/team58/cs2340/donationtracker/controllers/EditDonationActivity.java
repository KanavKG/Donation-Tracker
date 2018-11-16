package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import team58.cs2340.donationtracker.R;
import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.CurrUserLocal;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.LocationsLocal;

public class EditDonationActivity extends AppCompatActivity {

    private TextView value;

    private String mPhotoPath;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_donation);
        LocationsLocal locationManager = LocationsLocal.getInstance();
        CurrUserLocal userManager = CurrUserLocal.getInstance();
        TextView nameTxt = findViewById(R.id.name);
        Spinner locationSpinner = findViewById(R.id.locationSpinner);
        TextView shortDescription = findViewById(R.id.shortDescription);
        TextView fullDescription = findViewById(R.id.fullDescription);
        this.value = findViewById(R.id.value);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        TextView comment = findViewById(R.id.comment);
        Button takePhoto = findViewById(R.id.takePhotoBtn);
        this.photoView = findViewById(R.id.photo);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        //Disable button if user has no camera
        if (!hasCamera()) {
            takePhoto.setEnabled(false);
            takePhoto.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        Donation donation  = (Donation) intent.getSerializableExtra("donation");
        Location location = (Location) intent.getSerializableExtra("location");

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locationManager.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);
        int catSpinnerPos = categoryArrayAdapter.getPosition(donation.getCategory());
        categorySpinner.setSelection(catSpinnerPos);

        String imgName = donation.getName();
        imgName.replaceAll("\\s+","");

        StorageReference pathReference = mStorageRef.child("donationImages/" + imgName);

        try {
            final File localFile = File.createTempFile("donationImages", "jpg");
            pathReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap currDonationImage = BitmapFactory
                            .decodeFile(localFile.getAbsolutePath());
                    photoView.setImageBitmap(currDonationImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditDonationActivity.this,
                            "Current photo cannot be loaded!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        nameTxt.setText(donation.getName());
        String val = Double.toString(donation.getValue());
        value.setText(val);
        shortDescription.setText(donation.getShortDescription());
        fullDescription.setText(donation.getFullDescription());
        comment.setText(donation.getComment());

        if (userManager.getCurrentUserLocation() != null) {
            int spinnerPosition = locationAdapter.getPosition(location);
            locationSpinner.setSelection(spinnerPosition);
            locationSpinner.setEnabled(false);
        }
    }

    public boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * Function to launch camera
     * @param view takePhoto button view
     */
    public void launchCamera(View view) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.team58.fileprovider",
                        photoFile);
                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == REQUEST_IMAGE_CAPTURE) && (resultCode == RESULT_OK)) {
            File imgFile = new  File(mPhotoPath);
            if(imgFile.exists())            {
                photoView.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    /**
     * Function to get price of item as a double
     * @return Double value of item
     */
    public double getValue() {
        if ("".equals(this.value.getText().toString())) {
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
