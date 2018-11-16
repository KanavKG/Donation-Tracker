package team58.cs2340.donationtracker.controllers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.CurrUserLocal;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.R;
import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.models.Role;

public class DonationDetailsActivity extends AppCompatActivity {

    private ImageView photoView;
    private Bitmap donationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_item_detail);

        TextView name = findViewById(R.id.name);
        TextView location = findViewById(R.id.location);
        TextView shortDescription = findViewById(R.id.shortDescription);
        TextView fullDescription = findViewById(R.id.fullDescription);
        TextView value = findViewById(R.id.value);
        TextView comment = findViewById(R.id.comment);
        TextView timeStamp = findViewById(R.id.timeStamp);
        CurrUserLocal userManager = CurrUserLocal.getInstance();
        Button editBtn = findViewById(R.id.editBtn);
        this.photoView = findViewById(R.id.photoView);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        TextView catTxt = findViewById(R.id.category);

        Intent intent = getIntent();
        final Donation donation  = (Donation) intent.getSerializableExtra("donation");
        final Serializable locationExtra  = intent.getSerializableExtra("location");

        //assert userManager.getCurrentUser() != null;
        if (((userManager.getCurrentUserRole() == Role.LOCATIONEMPLOYEE) && userManager.
                getCurrentUserLocation().equals(donation.getLocation()))) {
            editBtn.setVisibility(View.VISIBLE);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addItemIntent = new Intent(
                            DonationDetailsActivity.this, EditDonationActivity.class);
                    addItemIntent.putExtra("donation", donation);
                    addItemIntent.putExtra("location", locationExtra);
                    startActivity(addItemIntent);
                }
            });
        } else {
            editBtn.setVisibility(View.GONE);
        }

        final Category cat = donation.getCategory();

        name.setText(donation.getName());
        catTxt.setText(cat.toString());
        location.setText(donation.getLocation());
        String val = "$" + donation.getValue();
        value.setText(val);
        String shortDes = "Short Description: " + donation.getShortDescription();
        shortDescription.setText(shortDes);
        String fullDes = "Full Description: " + donation.getFullDescription();
        fullDescription.setText(fullDes);
        String com = "Comment: " + donation.getComment();
        comment.setText(com);
        String time = "Time Stamp: " + donation.getTimeStamp();
        timeStamp.setText(time);

        String imgName = donation.getName();
        imgName.replaceAll("\\s+","");

        StorageReference pathReference = mStorageRef.child("donationImages/" + imgName);

        try {
            final File localFile = File.createTempFile("donationImages", "jpg");
            pathReference.getFile(localFile).addOnSuccessListener(
                    new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    donationImage = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    photoView.setImageBitmap(donationImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    switch (cat) {
                        case APPLIANCES:
                            photoView.setImageResource(R.drawable.ic_1appliances);
                            break;
                        case BABY:
                            photoView.setImageResource(R.drawable.ic_1baby);
                            break;
                        case BAGSANDACCESSORIES:
                            photoView.setImageResource(R.drawable.ic_1bags);
                            break;
                        case BOOKSANDMUSIC:
                            photoView.setImageResource(R.drawable.ic_1books);
                            break;
                        case CLOTHING:
                            photoView.setImageResource(R.drawable.ic_1clothing);
                            break;
                        case ELECTRONICS:
                            photoView.setImageResource(R.drawable.ic_1electronics);
                            break;
                        case FOOD:
                            photoView.setImageResource(R.drawable.ic_1food);
                            break;
                        case FURNITURE:
                            photoView.setImageResource(R.drawable.ic_1furniture);
                            break;
                        case MOVIESANDGAMES:
                            photoView.setImageResource(R.drawable.ic_1movie);
                            break;
                        case SPORTSANDOUTDOORS:
                            photoView.setImageResource(R.drawable.ic_1sports);
                            break;
                        case TOYS:
                            photoView.setImageResource(R.drawable.ic_1toys);
                            break;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
