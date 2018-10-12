package team58.cs2340.donationtracker.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team58.cs2340.donationtracker.Models.Location;
import team58.cs2340.donationtracker.R;

public class LocationListAdapter extends ArrayAdapter<Location> {
    private Context mContext;
    private int mResource;
    private TextView nameTxt;
    private TextView cityTxt;

    public LocationListAdapter(@NonNull Context context, int resource, ArrayList<Location> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String city = getItem(position).getCity();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        nameTxt = (TextView) convertView.findViewById(R.id.locationNameText);
        cityTxt = (TextView) convertView.findViewById(R.id.locationCityText);

        nameTxt.setText(name);
        cityTxt.setText(city);

        return convertView;
    }
}
