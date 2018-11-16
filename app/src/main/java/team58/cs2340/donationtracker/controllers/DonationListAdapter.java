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

        TextView nameTxt = convertView.findViewById(R.id.donationNameText);
        TextView valueTxt = convertView.findViewById(R.id.donationValueText);
        TextView descTxt = convertView.findViewById(R.id.donationDescText);
        TextView catTxt = convertView.findViewById(R.id.categoryText);
        ImageView donImg = convertView.findViewById(R.id.donationImg);

        nameTxt.setText(name);
        String val = "$" + value;
        valueTxt.setText(val);
        descTxt.setText(desc);
        catTxt.setText(cat.toString());
        switch (cat) {
            case APPLIANCES:
                donImg.setImageResource(R.drawable.ic_1appliances);
                break;
            case BABY:
                donImg.setImageResource(R.drawable.ic_1baby);
                break;
            case BAGSANDACCESSORIES:
                donImg.setImageResource(R.drawable.ic_1bags);
                break;
            case BOOKSANDMUSIC:
                donImg.setImageResource(R.drawable.ic_1books);
                break;
            case CLOTHING:
                donImg.setImageResource(R.drawable.ic_1clothing);
                break;
            case ELECTRONICS:
                donImg.setImageResource(R.drawable.ic_1electronics);
                break;
            case FOOD:
                donImg.setImageResource(R.drawable.ic_1food);
                break;
            case FURNITURE:
                donImg.setImageResource(R.drawable.ic_1furniture);
                break;
            case MOVIESANDGAMES:
                donImg.setImageResource(R.drawable.ic_1movie);
                break;
            case SPORTSANDOUTDOORS:
                donImg.setImageResource(R.drawable.ic_1sports);
                break;
            case TOYS:
                donImg.setImageResource(R.drawable.ic_1toys);
                break;
        }

        return convertView;
    }
}
