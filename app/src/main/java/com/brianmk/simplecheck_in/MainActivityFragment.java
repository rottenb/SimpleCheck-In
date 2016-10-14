package com.brianmk.simplecheck_in;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

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
                R.layout.main_trip_list,
                R.id.main_trip_list_item_textview,
                dummyTripList
        );

        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        ListView mainTripListView = (ListView) rootView.findViewById(R.id.main_trip_list);
        mainTripListView.setAdapter(mTripListAdapter);

        return rootView;
    }
}
