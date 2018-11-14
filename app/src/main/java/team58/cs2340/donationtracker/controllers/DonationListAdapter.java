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

import team58.cs2340.donationtracker.models.Category;
import team58.cs2340.donationtracker.models.Donation;
import team58.cs2340.donationtracker.R;

public class DonationListAdapter extends ArrayAdapter<Donation> {
    private Context mContext;
    private int mResource;
    private TextView nameTxt;
    private TextView valueTxt;
    private TextView descTxt;
    private ImageView donImg;

    // changed ArrayList -> List : weakened type
    public DonationListAdapter(@NonNull Context context, int resource, List<Donation> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String value = Double.toString(getItem(position).getValue());
        String desc = getItem(position).getShortDescription();
        Category cat = getItem(position).getCategory();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        nameTxt = (TextView) convertView.findViewById(R.id.donationNameText);
        valueTxt = (TextView) convertView.findViewById(R.id.donationValueText);
        descTxt = (TextView) convertView.findViewById(R.id.donationDescText);
        donImg = (ImageView) convertView.findViewById(R.id.donationImg);

        nameTxt.setText(name);
        valueTxt.setText("$" + value);
        descTxt.setText(desc);
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
