package com.brianmk.simplecheck_in;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rot on 2017-12-01
 *
 *  A custom list adapter for the trip entries in the database
 */

public class TripDataAdapter extends ArrayAdapter<TripData> {
    public TripDataAdapter(Context context, List<TripData> tripData) {
        super(context, 0, tripData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from this position
        TripData td = getItem(position);

        // Check if existing view is being reused, otherwise -> inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trip_list_item,
                                                parent,false);
        }

        // Get the views of the list
        TextView title = (TextView) convertView.findViewById(R.id.trip_list_item_textview);
        ImageView activity = (ImageView) convertView.findViewById(R.id.trip_list_activity_type);

        // Populate the views with data
        title.setText(td.getTitle());
        activity.setBackgroundResource(td.getActivityIcon());

        return convertView;
    }
}
