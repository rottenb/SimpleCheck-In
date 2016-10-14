package com.brianmk.simplecheck_in;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by rot on 2016-10-14.
 *
 * Displays information regarding current trip
 */

public class TripViewerActivity extends AppCompatActivity {
    private static final String LOG_TAG = TripViewerActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_viewer);


        Log.d(LOG_TAG, "displaying");
    }
}
