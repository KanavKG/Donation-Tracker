package team58.cs2340.donationtracker.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.R;

class DonationListAdapter extends ArrayAdapter<Donation> {
    private final Context mContext;
    private final int mResource;

    DonationListAdapter(@NonNull Context context, List<Donation> objects) {
        super(context, R.layout.layout_donationitem, objects);
        mContext = context;
        mResource = R.layout.layout_donationitem;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String value = Double.toString(Objects.requireNonNull(getItem(position)).getValue());
        String desc = Objects.requireNonNull(getItem(position)).getShortDescription();
        Category cat = Objects.requireNonNull(getItem(position)).getCategory();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTxt = (TextView) convertView.findViewById(R.id.donationNameText);
        TextView valueTxt = (TextView) convertView.findViewById(R.id.donationValueText);
        TextView descTxt = (TextView) convertView.findViewById(R.id.donationDescText);
        TextView catTxt = (TextView) convertView.findViewById(R.id.categoryText);
        ImageView donImg = (ImageView) convertView.findViewById(R.id.donationImg);

        nameTxt.setText(name);
        String val = "$" + value;
        valueTxt.setText(val);
        descTxt.setText(desc);
        catTxt.setText(cat.toString());
        if (cat == Category.APPLIANCES) {
            donImg.setImageResource(R.drawable.ic_1appliances);
        } else if (cat == Category.BABY) {
            donImg.setImageResource(R.drawable.ic_1baby);
        } else if (cat == Category.BAGSANDACCESSORIES) {
            donImg.setImageResource(R.drawable.ic_1bags);
        } else if (cat == Category.BOOKSANDMUSIC) {
            donImg.setImageResource(R.drawable.ic_1books);
        } else if (cat == Category.CLOTHING) {
            donImg.setImageResource(R.drawable.ic_1clothing);
        } else if (cat == Category.ELECTRONICS) {
            donImg.setImageResource(R.drawable.ic_1electronics);
        } else if (cat == Category.FOOD) {
            donImg.setImageResource(R.drawable.ic_1food);
        } else if (cat == Category.FURNITURE) {
            donImg.setImageResource(R.drawable.ic_1furniture);
        } else if (cat == Category.MOVIESANDGAMES) {
            donImg.setImageResource(R.drawable.ic_1movie);
        } else if (cat == Category.SPORTSANDOUTDOORS) {
            donImg.setImageResource(R.drawable.ic_1sports);
        } else if (cat == Category.TOYS) {
            donImg.setImageResource(R.drawable.ic_1toys);
        }

        return convertView;
    }
}
