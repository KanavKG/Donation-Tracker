package team58.cs2340.donationtracker.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import team58.cs2340.donationtracker.models.Location;
import team58.cs2340.donationtracker.R;

public class LocationListAdapter extends ArrayAdapter<Location> {
    private Context mContext;
    private int mResource;

    // changed ArrayList -> List : weakened type
    LocationListAdapter(@NonNull Context context, int resource, List<Location> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String city = Objects.requireNonNull(getItem(position)).getCity();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameTxt = (TextView) convertView.findViewById(R.id.locationNameText);
        TextView cityTxt = (TextView) convertView.findViewById(R.id.locationCityText);

        nameTxt.setText(name);
        cityTxt.setText(city);

        return convertView;
    }
}
