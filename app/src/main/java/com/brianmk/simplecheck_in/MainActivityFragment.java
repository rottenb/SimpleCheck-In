package com.brianmk.simplecheck_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private ArrayAdapter<String> mTripListAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] dummyTripListData = {
                "Bellingham - Chuckanut",
                "Bellingham - Galbraith",
                "Nelson - Giveout/Gold Creek",
                "Nelson - Mountain Station",
                "Nelson - North Shore",
                "Nelson - Svoboda Road",
                "North Vancouver - Fromme",
                "North Vancouver - Seymour",
                "West Vancouver - Cypress",
                "Squamish - Alice Lake",
                "Squamish - Diamond Head",
                "Squamish - Red Heather",
                "Fraser Valley - Sumas",
                "Fraser Valley - Ledge View",
                "Whistler - Bike Park",
                "Whistler - Cheakamus",
                "Whistler - Lost Lake",
        };

        List<String> dummyTripList = new ArrayList<>(Arrays.asList(dummyTripListData));

        mTripListAdapter = new ArrayAdapter<> (
                getActivity(),
                R.layout.trip_list_item,
                R.id.trip_list_item_textview,
                dummyTripList
        );

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        ListView tripListView = (ListView) rootView.findViewById(R.id.trip_list);
        tripListView.setAdapter(mTripListAdapter);

        tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "item clicked: " + position);
                Intent tripIntent = new Intent(getActivity(), TripEditorActivity.class);
                startActivity(tripIntent);
            }
        });

        return rootView;
    }
}
