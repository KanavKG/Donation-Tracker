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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import team58.cs2340.donationtracker.R;
import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.CurrUserLocal;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.LocationsLocal;

/**
 * Class to the edit donations activity
 */
public class EditDonationActivity extends AppCompatActivity {

    private TextView nameTxt;
    private Spinner locationSpinner;
    private TextView value;
    private TextView shortDescription;
    private TextView fullDescription;
    private Spinner categorySpinner;
    private TextView comment;
    boolean photoTaken = false;

    Bitmap currDonationImage;
    StorageReference mStorageRef;

    private String mPhotoPath;
    StorageReference pathReference;
    String nmID;
    FirebaseFirestore db;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_donation);
        LocationsLocal locationManager = LocationsLocal.getInstance();
        CurrUserLocal userManager = CurrUserLocal.getInstance();
        this.nameTxt = findViewById(R.id.name);
        nameTxt.setEnabled(false);
        this.locationSpinner = findViewById(R.id.locationSpinner);
        this.shortDescription = findViewById(R.id.shortDescription);
        this.fullDescription = findViewById(R.id.fullDescription);
        this.value = findViewById(R.id.value);
        this.categorySpinner = findViewById(R.id.categorySpinner);
        this.comment = findViewById(R.id.comment);
        Button takePhoto = findViewById(R.id.takePhotoBtn);
        this.photoView = findViewById(R.id.photo);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        //Disable button if user has no camera
        if (!hasCamera()) {
            takePhoto.setEnabled(false);
            takePhoto.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        Donation donation  = (Donation) intent.getSerializableExtra("donation");
        final Location location = (Location) intent.getSerializableExtra("location");

        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locationManager.getLocations());
        locationSpinner.setAdapter(locationAdapter);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);
        int catSpinnerPos = categoryArrayAdapter.getPosition(donation.getCategory());
        categorySpinner.setSelection(catSpinnerPos);

        nmID = donation.getName().replaceAll("[&\\s+]","") +
                "_" + donation.getLocation().replaceAll("[&\\s+]","");

        pathReference = mStorageRef.child("donationImages/" + nmID);

        try {
            final File localFile = File.createTempFile("donationImages", "jpg");
            pathReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    currDonationImage = BitmapFactory
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

    /**
     * Method to check if the device has a camera
     * @return boolean value of existance of camera
     */
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * Function to launch camera
     * @param view takePhoto button view
     */
    public void launchCamera(View view) {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile;
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
            photoTaken = true;
            if(imgFile.exists())            {
                photoView.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    public void onDeleteClicked(View view) {
        final Location location = (Location) locationSpinner.getSelectedItem();
        db.collection("donations").document(nmID)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditDonationActivity.this,
                                "Donation deleted!",
                                Toast.LENGTH_SHORT).show();
                        pathReference.delete();
                        Intent backtoLocationPageIntent = new Intent(
                                EditDonationActivity.this,
                                LocationPageActivity.class);
                        backtoLocationPageIntent.putExtra("location", location);
                        startActivity(backtoLocationPageIntent);
                    }
                });
    }

    public void onEditClicked(View view) {
        final Location location = (Location) locationSpinner.getSelectedItem();
        final Double value = Donation.getValue(this.value.getText().toString());
        final String shortDescription = this.shortDescription.getText().toString();
        final String fullDescription = this.fullDescription.getText().toString();
        final Category category = (Category) categorySpinner.getSelectedItem();
        String comment = this.comment.getText().toString();
        final Date[] donationTS = new Date[1];

        if (this.value.getText().toString().isEmpty()) {
            this.value.setError("Value cannot be empty!");
            this.value.requestFocus();
            return;
        }
        if (shortDescription.isEmpty()) {
            this.shortDescription.setError("Short description is required!");
            this.shortDescription.requestFocus();
            return;
        }
        if (fullDescription.isEmpty()) {
            this.fullDescription.setError("Full description is required!");
            this.fullDescription.requestFocus();
            return;
        }
        if (comment.isEmpty()) {
            comment = "";
        }

        final String finalComment = comment;
        db.collection("donations")
                .document(nmID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        donationTS[0] = document.getDate("timestamp");
                        db.collection("donations").document(nmID).delete();
                        final Map<String, Object> donationUpdated = new HashMap<>();
                        donationUpdated.put("name", nameTxt.getText().toString());
                        donationUpdated.put("location", location.getName());
                        donationUpdated.put("value", Double.toString(value));
                        donationUpdated.put("shortDescription", shortDescription);
                        donationUpdated.put("fullDescription", fullDescription);
                        donationUpdated.put("category", category.toString());
                        donationUpdated.put("comment", finalComment);
                        donationUpdated.put("timestamp", donationTS[0]);

                        db.collection("donations")
                                .document(nmID)
                                .set(donationUpdated)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(EditDonationActivity.this,
                                                "Updated donation in database!",
                                                Toast.LENGTH_SHORT).show();
                                        if (hasCamera() && photoTaken) {
                                            File photoFile = new File(mPhotoPath);
                                            Uri photoUri = Uri.fromFile(photoFile);
                                            StorageReference filePath = pathReference;
                                            filePath.putFile(photoUri).addOnSuccessListener(
                                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Toast.makeText(EditDonationActivity.this,
                                                                    "Updated image in storage.",
                                                                    Toast.LENGTH_SHORT).show();
                                                            Intent backtoLocationPageIntent =
                                                                    new Intent(EditDonationActivity.this,
                                                                            LocationPageActivity.class);
                                                            backtoLocationPageIntent.putExtra("location", location);
                                                            startActivity(backtoLocationPageIntent);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(EditDonationActivity.this,
                                                            "Failed to update image in storage.",
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent backtoLocationPageIntent = new Intent(
                                                            EditDonationActivity.this,
                                                            LocationPageActivity.class);
                                                    backtoLocationPageIntent.putExtra("location", location);
                                                    startActivity(backtoLocationPageIntent);
                                                }
                                            });
                                        } else {
                                            Intent backtoLocationPageIntent = new Intent(
                                                    EditDonationActivity.this,
                                                    LocationPageActivity.class);
                                            backtoLocationPageIntent.putExtra("location", location);
                                            startActivity(backtoLocationPageIntent);
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditDonationActivity.this,
                                                "Failed to update donation in database.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(EditDonationActivity.this,
                                "The donation you are seeking to edit does not exist!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditDonationActivity.this,
                            "Failed to retrieve the donation you are seeking to edit!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
